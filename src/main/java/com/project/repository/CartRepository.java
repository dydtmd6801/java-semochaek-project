package com.project.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.entity.Cart;
import com.project.entity.User;

public interface CartRepository extends JpaRepository<Cart, Long>{
	Optional<Cart> findByUser(User user);
}
