package com.project.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MailService {
	
	private final JavaMailSender mailSender;
	
	public void sendVerificationEmail(String toEmail, String verificationCode) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(toEmail);
		message.setSubject("이메일 인증 코드");
		message.setText("인증코드 : " + verificationCode);
		mailSender.send(message);
	}
}
