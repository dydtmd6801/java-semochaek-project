package com.project.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.data.CartRequest;
import com.project.data.CartResponseDTO;
import com.project.service.CartService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "http://127.0.0.1:5500")
@RestController
@RequiredArgsConstructor
public class CartController {
	
	private final CartService cartService;
	
	@GetMapping("/addCart")
	public ResponseEntity<?> addCart(@RequestParam("isbn") String isbn) {
		try {
			String email = SecurityContextHolder.getContext().getAuthentication().getName();
			cartService.saveCart(email, isbn);
			return ResponseEntity.ok("저장 완료");
		} catch (EntityNotFoundException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@GetMapping("/searchCart")
	public List<CartResponseDTO> searchCart() {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		return cartService.loadCartList(email);
	}
	
	@GetMapping("/deleteCart")
	public ResponseEntity<?> deleteCart(@RequestBody CartRequest request) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		cartService.removeCart(email, request.getIsbn());
		return ResponseEntity.ok("삭제 완료");
	}
}
