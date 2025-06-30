package com.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.entity.DetailOrder;
import com.project.entity.Order;

public interface DetailOrderRepository extends JpaRepository<DetailOrder, Long> {
	List<DetailOrder> findByOrder(Order order);
}
