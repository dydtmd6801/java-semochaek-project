package com.project.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.project.ProjectApplication;
import com.project.data.NaverBookItem;
import com.project.data.NaverBookResponse;
import com.project.entity.Book;
import com.project.repository.BookRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NaverBookService {
	
	private final BookRepository bookRepository;
	private final RestTemplate restTemplate = new RestTemplate();
	
	@Value("${naver.client-id}")
	private String clientId;
	
	@Value("${naver.client-secret}")
	private String clientSecret;
	
	public void fetchAndSaveBooks(String keyword) {
		String apiUrl = "https://openapi.naver.com/v1/search/book.json?query=" + keyword + "&display=100";
		
		HttpHeaders headers = new HttpHeaders();
		headers.set("X-Naver-Client-Id", clientId);
		headers.set("X-Naver-Client-Secret", clientSecret);
		HttpEntity<String> entity = new HttpEntity<>(headers);
		
		ResponseEntity<NaverBookResponse> response = restTemplate.exchange(apiUrl, HttpMethod.GET, entity, NaverBookResponse.class);
		
		if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            List<Book> books = response.getBody().getItems().stream()
                    .map(this::toBookEntity)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

            bookRepository.saveAll(books);
        }
	}
	
	private Book toBookEntity(NaverBookItem item) {
		String isbn13 = extractIsbn13(item.getIsbn());
		if (isbn13 == null || bookRepository.findByIsbn(isbn13).isPresent()) {
			return null;
		}
		
		return Book.builder()
				.title(item.getTitle())
				.author(item.getAuthor())
				.publisher(item.getPublisher())
				.isbn(isbn13)
				.image(item.getIamge())
				.description(item.getDescription())
				.price(item.getPrice())
				.build();
	}

	private String extractIsbn13(String rawIsbn) {
		if (rawIsbn == null) return null;
		String[] parts = rawIsbn.split(" ");
		return parts.length == 2 ? parts[1] : rawIsbn;
	}
}
