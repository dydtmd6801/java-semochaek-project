package com.project.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.data.OrderResponseDTO;
import com.project.entity.Order;
import com.project.entity.User;
import com.project.repository.OrderRepository;
import com.project.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {
	
	private final UserRepository userRepository;
	private final OrderRepository orderRepository;
	
	public List<OrderResponseDTO> loadOrderList(String email) {
		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new EntityNotFoundException("해당 유저가 존재하지 않습니다."));
		
		List<Order> orders = orderRepository.findAllByUser(user);
		
		return orders.stream()
			.map(order -> OrderResponseDTO.builder()
				.orderId(order.getOrderId())
				.merchantId(order.getMerchantId())
				.amount(order.getAmount())
				.paidAt(order.getPaidAt())
				.build()
			)
		.toList();
	}
}
