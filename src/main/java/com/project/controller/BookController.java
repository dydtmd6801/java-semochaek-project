package com.project.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.data.BookDTO;
import com.project.data.BookDetailDTO;
import com.project.service.BookService;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "http://127.0.0.1:5500")
@RequiredArgsConstructor
@RestController
@RequestMapping("/books")
public class BookController {
	
	private final BookService bookService;
	
//	@GetMapping
//	public PagedModel<EntityModel<BookDTO>> getBooksPage (
//			@RequestParam(name = "page", defaultValue = "1") int page,
//			@RequestParam(name = "size", defaultValue = "20") int size,
//			@RequestParam(name = "sortBy", defaultValue = "title") String sortBy,
//			@RequestParam(name = "direction", defaultValue = "asc") String direction,
//			PagedResourcesAssembler<BookDTO> assembler
//			) {
//		Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.fromString(direction), sortBy));
//		
//		long start = System.currentTimeMillis();
//		
//		Page<BookDTO> books = bookService.getAllBooksPage(pageable);
//		
//		long end = System.currentTimeMillis();
//		System.out.println("[Page] 소요 시간 : " + (end - start) + "ms");
//		
//		return assembler.toModel(books);
//	}
	
	@GetMapping()
	public Slice<BookDTO> getBooksSlice (
			@RequestParam(name = "page", defaultValue = "1") int page,
			@RequestParam(name = "size", defaultValue = "20") int size,
			@RequestParam(name = "sortBy", defaultValue = "title") String sortBy,
			@RequestParam(name = "direction", defaultValue = "asc") String direction) {
		Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.fromString(direction), sortBy));
		
		long start = System.currentTimeMillis();
		
		Slice<BookDTO> books = bookService.getAllBooksSlice(pageable);
		
		long end = System.currentTimeMillis();
		System.out.println("[Slice] 소요 시간 : " + (end - start) + "ms");
		
		return books;
	}
	
	@GetMapping("/detail")
	public BookDetailDTO getDetail(@RequestParam("isbn") String isbn) {
		return bookService.getBookDetail(isbn);
	}
	
	@GetMapping("/count")
	public Long getBookCount() {
		return bookService.bookCount();
	}
}
