package com.project.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.entity.Book;
import com.project.entity.Favorite;
import com.project.entity.User;
import com.project.repository.BookRepository;
import com.project.repository.FavoriteRepository;
import com.project.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FavoriteService {
	
	private final FavoriteRepository favoriteRepository;
	private final UserRepository userRepository;
	private final BookRepository bookRepository;
	
	public boolean createFavoriteBook(String email, String isbn) {
		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new EntityNotFoundException("존재하지 않는 사용자 입니다."));
		Book book = bookRepository.findByIsbn(isbn)
				.orElseThrow(() -> new EntityNotFoundException("존재하지 않는 도서 입니다."));
		
		List<Favorite> favorites = favoriteRepository.findAllByUser(user);
		
		boolean checkDuplicate = favorites.stream()
				.anyMatch(favorite -> favorite.getBook().equals(book));
		
		if (checkDuplicate) {
			return false;
		}
		
		Favorite favorite = Favorite.builder()
				.user(user)
				.book(book)
				.build();
		
		favoriteRepository.save(favorite);
		return true;
	}
}
