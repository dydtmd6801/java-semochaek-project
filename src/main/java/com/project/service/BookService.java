package com.project.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import com.project.controller.AuthController;
import com.project.data.BookDTO;
import com.project.data.BookDetailDTO;
import com.project.entity.Book;
import com.project.repository.BookRepository;
import com.project.repository.BookRepositorySlice;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookService {
	
	private final BookRepository bookRepository;
	private final BookRepositorySlice bookRepositorySlice;
	
//	public Page<BookDTO> getAllBooksPage(Pageable pageable) {
//		Page<Book> books = bookRepository.findAllByPriceNot(0, pageable);
//		
//		return books.map((Book book) -> BookDTO.builder()
//						.title(book.getTitle())
//						.author(book.getAuthor())
//						.publisher(book.getPublisher())
//						.isbn(book.getIsbn())
//						.image(book.getImage())
//						.price(book.getPrice())
//						.build());
//	}
	
	public Slice<BookDTO> getAllBooksSlice(Pageable pageable) {
		Slice<Book> books = bookRepositorySlice.findAllByPriceNot(0, pageable);
		
		return books.map(book -> BookDTO.builder()
						.title(book.getTitle())
						.author(book.getAuthor())
						.publisher(book.getPublisher())
						.isbn(book.getIsbn())
						.image(book.getImage())
						.price(book.getPrice())
						.build());
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

