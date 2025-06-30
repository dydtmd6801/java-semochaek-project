package com.project.service;

import java.sql.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.data.OrderItemRequest;
import com.project.data.PaymentVerifyRequest;
import com.project.entity.Book;
import com.project.entity.DetailOrder;
import com.project.entity.Order;
import com.project.repository.BookRepository;
import com.project.repository.DetailOrderRepository;
import com.project.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentService {
	
	private final OrderRepository orderRepository;
	private final DetailOrderRepository detailOrderRepository;
	private final BookRepository bookRepository;
	
	private final RestTemplate restTemplate = new RestTemplate();
	
	@Value("${iamport.key}")
	private String IMP_KEY;
	
	@Value("${iamport.secret}")
	private String IMP_SECRET;
	
	public void verifyAndSave(PaymentVerifyRequest request) {
		String token = getAccessToken();
		
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(token);
		HttpEntity<String> entity = new HttpEntity<>(headers);
		
		ResponseEntity<Map> response = restTemplate.exchange("https://api.iamport.kr/payments/" + request.getImpUid(), 
				HttpMethod.GET,
				entity,
				Map.class
		);
		
		Map<String, Object> data = (Map<String, Object>) response.getBody().get("response");
		Integer amount = (Integer) data.get("amount");
		String status = (String) data.get("status");
		Date paidAt = new Date(((Number) data.get("paid_at")).longValue() * 1000);
		
		if (!status.equals("paid")) {
			throw new RuntimeException("결제 실패 상태 : " + status);
		}
		
		int total = request.getItems().stream()
				.mapToInt(i -> i.getQuantity() * i.getPrice())
				.sum();
		
		if (!amount.equals(total)) {
			throw new RuntimeException("결제 금액 불일치: PG = " + amount + ", 클라이언트 = " + total);
		}
		
		Order order = Order.builder()
				.merchantId(request.getMerchantUid())
				.impUid(request.getImpUid())
				.amount(amount)
				.status(status)
				.paidAt(paidAt)
				.build();
		
		orderRepository.save(order);
		
		for (OrderItemRequest item : request.getItems()) {
			Book book = bookRepository.findByIsbn(item.getBookIsbn()).orElseThrow();
			DetailOrder detail = DetailOrder.builder()
					.order(order)
					.book(book)
					.quantity(item.getQuantity())
					.price(item.getPrice())
					.build();
			
			detailOrderRepository.save(detail);
		}
	}
	
	private String getAccessToken() {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			
			Map<String, String> creds = Map.of(
					"imp_key", IMP_KEY, 
					"imp_secret", IMP_SECRET
			);
			
			ObjectMapper mapper = new ObjectMapper();
			String body = mapper.writeValueAsString(creds);
			
			HttpEntity<String> request = new HttpEntity<String>(body, headers);
			
			ResponseEntity<Map> response = restTemplate.postForEntity("https://api.iamport.kr/users/getToken", request, Map.class);
			return (String) ((Map) response.getBody().get("response")).get("access_token");
		} catch (JsonProcessingException e) {
			throw new RuntimeException("JSON 변환 중 오류 발생", e);
		}
	}
}
