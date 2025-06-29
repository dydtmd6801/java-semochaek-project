package com.project.service;

import org.springframework.stereotype.Service;

import com.project.data.CartRequest;
import com.project.entity.Book;
import com.project.entity.Cart;
import com.project.entity.User;
import com.project.repository.CartRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartService {
	
	private final CartRepository cartRepository;
	private final UserService userService;
	private final BookService bookService;
	
	public void saveCart(String email, CartRequest request) {
		User user = userService.loadUser(email);
		Book book = bookService.loadBook(request.getIsbn());
		
		Cart cart = Cart.builder()
				.user(user)
				.book(book)
				.build();
		
		cartRepository.save(cart);
	}
}
