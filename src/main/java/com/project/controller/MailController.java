package com.project.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.project.data.VerifyRequest;
import com.project.service.MailService;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "http://127.0.0.1:5500")
@RestController
@RequiredArgsConstructor
public class MailController {
	
	private final MailService mailService;
	private final Map<String, String> verificationCodes = new HashMap<>();
	
	@PostMapping("/send-code")
	public ResponseEntity<?> sendCode(@RequestBody String email) {
		String code = UUID.randomUUID().toString().substring(0, 6);
		mailService.sendVerificationEmail(email, code);
		verificationCodes.put(email, code);
		return ResponseEntity.ok("인증코드가 전송되었습니다.");
	}
	
	@PostMapping("/verify-code")
	public ResponseEntity<?> verifyCode(@RequestBody VerifyRequest request) {
		String savedCode = verificationCodes.get(request.getEmail());
		System.out.println("saved : " + savedCode + " , code : " + request.getCode());
		if (savedCode != null && savedCode.equals(request.getCode())) {
			return ResponseEntity.ok("이메일 인증 완료");
		} else {
			return ResponseEntity.badRequest().body("코드가 일치하지 않습니다.");
		}
	}
}
