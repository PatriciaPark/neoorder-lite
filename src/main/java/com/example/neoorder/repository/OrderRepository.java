package com.example.neoorder.repository;

import com.example.neoorder.model.Order;
import com.example.neoorder.model.OrderStatus;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
// import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByStatus(OrderStatus status);
    
    // Spring Data JPA + Pageable 기반 검색 메서드
    Page<Order> findByCustomerNameContainingIgnoreCase(String customerName, Pageable pageable);
    Page<Order> findByStatus(String status, Pageable pageable);

}
