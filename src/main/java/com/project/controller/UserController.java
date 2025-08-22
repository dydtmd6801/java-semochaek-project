package com.project.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.project.data.ApproveRequest;
import com.project.data.AuthRequest;
import com.project.data.FindEmailRequest;
import com.project.data.RegisterRequest;
import com.project.service.UserService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "http://127.0.0.1:5500")
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
	
	@PostMapping("/findEmail")
	public ResponseEntity<?> findEmail(@RequestBody FindEmailRequest request) {
		try {
			String email = userService.loadEmail(request.getName(), request.getPhoneNumber());
			return ResponseEntity.ok(email);
		} catch (EntityNotFoundException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PostMapping("/resetPassword")
	public ResponseEntity<?> resetPassword (@RequestBody AuthRequest request) {
		try {
			userService.updatePassword(request.getEmail(), request.getPassword());
			return ResponseEntity.ok("비밀번호 수정 완료");
		} catch (EntityNotFoundException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PostMapping("/getUserInfo")
	public ResponseEntity<?> getUserInfo() {
		try {
			String email = SecurityContextHolder.getContext().getAuthentication().getName();
			return ResponseEntity.ok(userService.loadUserInfo(email));
		} catch (EntityNotFoundException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PostMapping("/changeName")
	public ResponseEntity<?> changeName (@RequestBody String name) {
		try {
			String email = SecurityContextHolder.getContext().getAuthentication().getName();
			userService.updateName(email, name);
			return ResponseEntity.ok("업데이트 완료");
		} catch (EntityNotFoundException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PostMapping("/changeTelephone")
	public ResponseEntity<?> changeTelephone (@RequestBody String telephone) {
		try {
			String email = SecurityContextHolder.getContext().getAuthentication().getName();
			userService.updateTelephone(email, telephone);
			return ResponseEntity.ok("업데이트 완료");
		} catch (EntityNotFoundException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
}
