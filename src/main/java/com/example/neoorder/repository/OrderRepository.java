package com.example.neoorder.repository;

import com.example.neoorder.model.Order;
import com.example.neoorder.model.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
// import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByStatus(OrderStatus status);
    
    // Spring Data JPA + Pageable 기반 검색 메서드
    Page<Order> findByCustomerNameContainingIgnoreCase(String customerName, Pageable pageable);
    Page<Order> findByStatus(OrderStatus status, Pageable pageable);
    
    // 고객명과 상태를 동시에 검색하는 메서드
    Page<Order> findByCustomerNameContainingIgnoreCaseAndStatus(String customerName, OrderStatus status, Pageable pageable);
    
    // 아이템명 검색 메서드
    Page<Order> findByItemContainingIgnoreCase(String item, Pageable pageable);
    
    // 아이템명과 고객명을 함께 검색하는 메서드
    Page<Order> findByItemContainingIgnoreCaseAndCustomerNameContainingIgnoreCase(String item, String customerName, Pageable pageable);
    
    // 아이템명과 상태를 함께 검색하는 메서드
    Page<Order> findByItemContainingIgnoreCaseAndStatus(String item, OrderStatus status, Pageable pageable);
    
    // 아이템명, 고객명, 상태를 함께 검색하는 메서드
    Page<Order> findByItemContainingIgnoreCaseAndCustomerNameContainingIgnoreCaseAndStatus(String item, String customerName, OrderStatus status, Pageable pageable);

    List<Order> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);

}
