package com.project.controller;

import java.util.Collections;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.project.data.AuthRequest;
import com.project.util.JwtUtil;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AuthController {
	
	private final AuthenticationManager authenticationManager;
	private final JwtUtil jwtUtil;
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody AuthRequest request) {
		try {		
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
			);
			
			org.springframework.security.core.userdetails.User userDetails = (User) authentication.getPrincipal();
			
			String role = userDetails.getAuthorities().iterator().next().getAuthority();
			
			String token = jwtUtil.generateToken(request.getEmail(), role);
			return ResponseEntity.ok(Collections.singletonMap("token", token));
		} catch (BadCredentialsException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("아이디 비밀번호 오류");
		} catch (AuthenticationException e){
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 인증 실패");
		}
	}
	
	@GetMapping("/api/protected")
	public ResponseEntity<String> protectedApi() {
		return ResponseEntity.ok("인증된 사용만 접근 가능");
	}
}
