package com.example.neoorder.controller;

import com.example.neoorder.model.Order;
import com.example.neoorder.model.OrderStatus;
import com.example.neoorder.repository.OrderRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * OrderController에 대한 통합 테스트
 */
@SpringBootTest
@AutoConfigureMockMvc
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCreateOrder() throws Exception {
        // Given
        Order order = new Order();
        order.setItem("테스트 상품");
        order.setCustomerName("홍길동");
        
        // When & Then
        mockMvc.perform(post("/api/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(order)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.item").value("테스트 상품"))
                .andExpect(jsonPath("$.customerName").value("홍길동"))
                .andExpect(jsonPath("$.status").value("RECEIVED"));
    }
    
    @Test
    public void testGetOrders() throws Exception {
        // Given
        Order order = new Order();
        order.setItem("테스트 상품");
        order.setCustomerName("홍길동");
        order.setStatus(OrderStatus.RECEIVED);
        orderRepository.save(order);
        
        // When & Then
        mockMvc.perform(get("/api/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].item").value("테스트 상품"))
                .andExpect(jsonPath("$.content[0].customerName").value("홍길동"));
    }
    
    @Test
    public void testUpdateOrderStatus() throws Exception {
        // Given
        Order order = new Order();
        order.setItem("테스트 상품");
        order.setCustomerName("홍길동");
        order.setStatus(OrderStatus.RECEIVED);
        Order savedOrder = orderRepository.save(order);
        
        // When & Then
        mockMvc.perform(put("/api/orders/" + savedOrder.getId() + "/status"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("SHIPPING"));
    }
    
    @Test
    public void testDeleteOrder() throws Exception {
        // Given
        Order order = new Order();
        order.setItem("테스트 상품");
        order.setCustomerName("홍길동");
        order.setStatus(OrderStatus.RECEIVED);
        Order savedOrder = orderRepository.save(order);
        
        // When & Then
        mockMvc.perform(delete("/api/orders/" + savedOrder.getId()))
                .andExpect(status().isOk());
        
        // 삭제 확인
        mockMvc.perform(get("/api/orders/" + savedOrder.getId()))
                .andExpect(status().isNotFound());
    }
} 