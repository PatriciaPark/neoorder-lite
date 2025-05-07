package com.example.neoorder.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Order 모델에 대한 단위 테스트
 */
public class OrderTest {

    @Test
    public void testOrderCreation() {
        // Given
        Order order = new Order();
        
        // When
        order.setId(1L);
        order.setItem("테스트 상품");
        order.setCustomerName("홍길동");
        order.setStatus(OrderStatus.RECEIVED);
        
        // Then
        assertEquals(1L, order.getId());
        assertEquals("테스트 상품", order.getItem());
        assertEquals("홍길동", order.getCustomerName());
        assertEquals(OrderStatus.RECEIVED, order.getStatus());
    }
    
    @Test
    public void testOrderDefaultStatus() {
        // Given
        Order order = new Order();
        
        // When
        // 기본 생성자로 생성된 Order의 상태는 RECEIVED로 설정됨
        
        // Then
        assertEquals(OrderStatus.RECEIVED, order.getStatus());
    }
} 