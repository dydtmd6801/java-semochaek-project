package com.project.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.data.OrderResponse;
import com.project.service.OrderService;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "http://127.0.0.1:5500")
@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {
	
	private final OrderService orderService;
	
	@GetMapping("/list")
	public ResponseEntity<List<OrderResponse>> searchPayment() {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		return ResponseEntity.ok(orderService.getOrderList(email));
	}
}
