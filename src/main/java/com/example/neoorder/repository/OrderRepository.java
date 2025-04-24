package com.example.neoorder.repository;

import com.example.neoorder.model.Order;
import com.example.neoorder.model.OrderStatus;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByStatus(OrderStatus status);

}
