package com.project.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.project.data.BookDTO;
import com.project.entity.Book;
import com.project.repository.BookRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookService {
	
	private final BookRepository bookRepository;
	
	public List<BookDTO> getAllBooks() {
		List<Book> books = bookRepository.findAll();
		
		return books.stream()
				.map((Book book) -> BookDTO.builder()
						.title(book.getTitle())
						.author(book.getAuthor())
						.publisher(book.getPublisher())
						.isbn(book.getIsbn())
						.image(book.getImage())
						.price(book.getPrice())
						.build())
				.collect(Collectors.toList());
	}
}

