package com.project.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.project.data.BookDTO;
import com.project.data.BookDetailDTO;
import com.project.entity.Book;
import com.project.repository.BookRepository;

import jakarta.persistence.EntityNotFoundException;
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
	
	public BookDetailDTO getBookDetail(String isbn) {
		Optional<Book> book = bookRepository.findByIsbn(isbn);
		
		if (book.isPresent()) {
			return BookDetailDTO.builder()
					.title(book.get().getTitle())
					.author(book.get().getAuthor())
					.publisher(book.get().getPublisher())
					.price(book.get().getPrice())
					.image(book.get().getImage())
					.description(book.get().getDescription())
					.isbn(book.get().getIsbn())
					.build();
		} else {
			return null;
		}
	}
	
	public Book loadBook(String isbn) {
		return bookRepository.findByIsbn(isbn)
				.orElseThrow(() -> new EntityNotFoundException("존재하지 않는 책입니다."));
	}
}

