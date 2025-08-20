package com.project.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.data.DetailOrderResponse;
import com.project.data.OrderResponse;
import com.project.entity.DetailOrder;
import com.project.entity.Order;
import com.project.entity.User;
import com.project.repository.DetailOrderRepository;
import com.project.repository.OrderRepository;
import com.project.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {
	
	private final UserRepository userRepository;
	private final OrderRepository orderRepository;
	private final DetailOrderRepository detailOrderRepository;
	
	public List<OrderResponse> getOrderList (String email) {
		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new EntityNotFoundException("존재하지 않는 회원입니다."));
		
		List<Order> orders = orderRepository.findAllByUser(user);
		
		return orders.stream().map(order -> {
			List<DetailOrder> detailOrders = detailOrderRepository.findByOrder(order);
			List<DetailOrderResponse> response = detailOrders.stream()
					.map(details -> new DetailOrderResponse(
							details.getBook().getImage(),
							details.getBook().getIsbn(),
							details.getBook().getTitle(),
							details.getQuantity(),
							details.getPrice()
					))
					.toList();
			
			return new OrderResponse(
					order.getMerchantId(),
					order.getAmount(),
					order.getPaidAt(),
					response
			);
		}).toList();
	}
}
