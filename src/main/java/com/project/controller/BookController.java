package com.project.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.data.BookDTO;
import com.project.data.BookDetailDTO;
import com.project.service.BookService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/books")
public class BookController {
	
	private final BookService bookService;
	
	@GetMapping
	public List<BookDTO> getBooks() {
		return bookService.getAllBooks();
	}
	
	@GetMapping("/detail")
	public BookDetailDTO getDetail(@RequestParam("isbn") String isbn) {
		return bookService.getBookDetail(isbn);
	}
}
