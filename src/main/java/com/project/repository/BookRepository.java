package com.project.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.project.entity.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
	Optional<Book> findByIsbn(String isbn);
	Page<Book> findAll(Pageable pageable);
}
