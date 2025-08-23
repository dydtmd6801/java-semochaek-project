package com.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.entity.Book;
import com.project.entity.Favorite;
import com.project.entity.User;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
	List<Favorite> findAllByUser(User user);
	void deleteByUserAndBook(User user, Book book);
}
