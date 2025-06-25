package com.project.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.project.data.ApproveRequest;
import com.project.data.RegisterRequest;
import com.project.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserController {
	
	private final UserService userService;
	
	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
		try {
			userService.registerUser(request);
			return ResponseEntity.ok("회원가입 완료");
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PostMapping("/api/admin/approve")
	public ResponseEntity<?> approve(@RequestBody ApproveRequest request) {
		userService.updateStatus(request.getEmail());
		return ResponseEntity.ok("승인 완료");
	}
	
}
