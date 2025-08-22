package com.project.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.data.RegisterRequest;
import com.project.entity.User;
import com.project.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
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
//				.address(request.getAddress())
//				.detailAddress(request.getDetailAddress())
//				.postNumber(request.getPostNumber())
				.build();
				
		userRepository.save(user);
	}
	
	@Transactional
	public void updateStatus(String email) {
		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new EntityNotFoundException("해당 이메일이 존재하지 않습니다."));
		
		user.setStatus("Y");
	}
	
	public String loadStatus(String email) {
		return userRepository.findByEmail(email)
				.orElseThrow(() -> new EntityNotFoundException("해앋 이메일이 존재하지 않습니다."))
				.getStatus();
	}
	
	public String loadEmail(String name, String telephone) {
		return userRepository.findByNameAndTelephone(name, telephone)
				.orElseThrow(() -> new EntityNotFoundException("존재하는 사용자가 아닙니다."))
				.getEmail();		
	}
	
	@Transactional
	public void updatePassword(String email, String password) {
		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new EntityNotFoundException("존재하지 않는 사용자 입니다."));
		
		user.setPassword(passwordEncoder.encode(password));
	}
	
	public User loadUser(String email) {
		return userRepository.findByEmail(email)
				.orElseThrow(() -> new EntityNotFoundException("존재하지 않는 사용자 입니다."));
	}
	
	public void updateName(String email, String name) {
		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new EntityNotFoundException("존재하지 않는 사용자 입니다."));
		
		user.setName(name);
	}
	
	public void updateTelephone(String email, String telephone) {
		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new EntityNotFoundException("존재하지 않는 사용자 입니다."));
		
		user.setTelephone(telephone);
	}
}
