package com.example.neoorder.repository;

import com.example.neoorder.model.Order;
import com.example.neoorder.model.OrderStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * OrderRepository에 대한 통합 테스트
 */
@DataJpaTest
public class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @Test
    public void testSaveAndFindById() {
        // Given
        Order order = new Order();
        order.setItem("테스트 상품");
        order.setCustomerName("홍길동");
        order.setStatus(OrderStatus.RECEIVED);
        
        // When
        Order savedOrder = orderRepository.save(order);
        Order foundOrder = orderRepository.findById(savedOrder.getId()).orElse(null);
        
        // Then
        assertNotNull(foundOrder);
        assertEquals("테스트 상품", foundOrder.getItem());
        assertEquals("홍길동", foundOrder.getCustomerName());
        assertEquals(OrderStatus.RECEIVED, foundOrder.getStatus());
    }
    
    @Test
    public void testFindByStatus() {
        // Given
        Order order1 = new Order();
        order1.setItem("상품1");
        order1.setCustomerName("고객1");
        order1.setStatus(OrderStatus.RECEIVED);
        
        Order order2 = new Order();
        order2.setItem("상품2");
        order2.setCustomerName("고객2");
        order2.setStatus(OrderStatus.SHIPPING);
        
        orderRepository.save(order1);
        orderRepository.save(order2);
        
        // When
        List<Order> receivedOrders = orderRepository.findByStatus(OrderStatus.RECEIVED);
        
        // Then
        assertEquals(1, receivedOrders.size());
        assertEquals("상품1", receivedOrders.get(0).getItem());
    }
    
    @Test
    public void testFindByCustomerNameContainingIgnoreCase() {
        // Given
        Order order1 = new Order();
        order1.setItem("상품1");
        order1.setCustomerName("홍길동");
        order1.setStatus(OrderStatus.RECEIVED);
        
        Order order2 = new Order();
        order2.setItem("상품2");
        order2.setCustomerName("김철수");
        order2.setStatus(OrderStatus.SHIPPING);
        
        orderRepository.save(order1);
        orderRepository.save(order2);
        
        // When
        Page<Order> page = orderRepository.findByCustomerNameContainingIgnoreCase("홍", PageRequest.of(0, 10));
        
        // Then
        assertEquals(1, page.getTotalElements());
        assertEquals("상품1", page.getContent().get(0).getItem());
    }
} 