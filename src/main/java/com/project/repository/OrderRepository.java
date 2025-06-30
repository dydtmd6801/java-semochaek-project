package com.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.entity.Order;

public interface OrderRepository extends JpaRepository<Order, String> {

}
