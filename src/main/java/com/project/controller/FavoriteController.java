package com.project.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.service.FavoriteService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "127.0.0.1:5500")
@RestController
@RequiredArgsConstructor
@RequestMapping("/favorite")
public class FavoriteController {
	
	private final FavoriteService favoriteService;
	
	@GetMapping("/add")
	public ResponseEntity<?> addFavoriteBook(@RequestParam("isbn") String isbn) {
		try {
			String email = SecurityContextHolder.getContext().getAuthentication().getName();
			boolean checkDuplicate = favoriteService.createFavoriteBook(email, isbn);
			if (!checkDuplicate) {
				return ResponseEntity.ok("duplicate");
			}
			return ResponseEntity.ok("success");
		} catch (EntityNotFoundException e) {
			return ResponseEntity.badRequest().body("잘못된 요청입니다.");
		}
	}
}
