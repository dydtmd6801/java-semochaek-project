package com.project.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.data.RegisterRequest;
import com.project.entity.User;
import com.project.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	
	private final UserRepository userRepository;
	private final BCryptPasswordEncoder passwordEncoder;
	
	public void registerUser(RegisterRequest request) {
		if (userRepository.findByEmail(request.getEmail()).isPresent()) {
			throw new IllegalArgumentException("중복되는 이메일입니다.");
		}
		
		User user = User.builder()
				.email(request.getEmail())
				.password(passwordEncoder.encode(request.getPassword()))
				.name(request.getName())
				.telephone(request.getPhoneNumber())
				.role("ROLE_USER")
				.status("N")
				.address(request.getAddress())
				.detailAddress(request.getDetailAddress())
				.postNumber(request.getPostNumber())
				.build();
				
		userRepository.save(user);
	}
}
