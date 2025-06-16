package com.project.scheduler;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.project.service.NaverBookService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BookFetcherScheduler implements CommandLineRunner{
	
	private final NaverBookService naverBookService;

	@Override
	public void run(String... args) throws Exception {
		for (char c = '가'; c <= '힣'; c += 100) {
			naverBookService.fetchAndSaveBooks(String.valueOf(c));
		}
		
		for (char c = '0'; c <= '9'; c++) {
			naverBookService.fetchAndSaveBooks(String.valueOf(c));
		}
		
		for (char c = 'A'; c <= 'Z'; c++) {
			naverBookService.fetchAndSaveBooks(String.valueOf(c));
		}
	}
}
