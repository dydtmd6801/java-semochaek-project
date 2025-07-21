package com.project.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import com.project.entity.Book;

public interface BookRepositorySlice extends JpaRepository<Book, Long> {
	Slice<Book> findAllByPriceNot(int price, Pageable pageable);
}
