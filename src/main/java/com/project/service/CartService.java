package com.project.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.project.data.CartResponseDTO;
import com.project.entity.Book;
import com.project.entity.Cart;
import com.project.entity.User;
import com.project.repository.CartRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartService {
	
	private final CartRepository cartRepository;
	private final UserService userService;
	private final BookService bookService;
	
	public void saveCart(String email, String isbn) {
		User user = userService.loadUser(email);
		Book book = bookService.loadBook(isbn);
		
		Cart cart = Cart.builder()
				.user(user)
				.book(book)
				.build();
		
		cartRepository.save(cart);
	}
	
	public List<CartResponseDTO> loadCartList(String email) {
		User user = userService.loadUser(email);
		List<Cart> carts = cartRepository.findAllByUser(user);
		
		return carts.stream()
				.map(cart -> CartResponseDTO.builder()
						.title(cart.getBook().getTitle())
						.isbn(cart.getBook().getIsbn())
						.image(cart.getBook().getImage())
						.build())
				.collect(Collectors.toList());
	}
	
	@Transactional
	public void removeCart(String email, String isbn) {
		User user = userService.loadUser(email);
		Book book = bookService.loadBook(isbn);
		
		cartRepository.deleteByUserAndBook(user, book);
	}
}
