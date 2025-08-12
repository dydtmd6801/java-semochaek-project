package com.project.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.data.OrderResponse;
import com.project.data.PaymentVerifyRequest;
import com.project.service.PaymentService;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "http://127.0.0.1:5500")
@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {
	
	private final PaymentService paymentService;
	
	@PostMapping("/verify")
	public ResponseEntity<String> verify(@RequestBody PaymentVerifyRequest request) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		paymentService.verifyAndSave(request, email);
		return ResponseEntity.ok("결제 검증 및 저장 완료");
	}
	
	@GetMapping("/search")
	public ResponseEntity<List<OrderResponse>> searchPayment() {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		return ResponseEntity.ok(paymentService.getOrderList(email));
	}
}
