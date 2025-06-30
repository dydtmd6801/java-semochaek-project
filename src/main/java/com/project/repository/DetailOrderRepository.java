package com.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.entity.DetailOrder;

public interface DetailOrderRepository extends JpaRepository<DetailOrder, Long> {

}
