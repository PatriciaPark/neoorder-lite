package com.example.neoorder.model;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@EntityListeners(AuditingEntityListener.class)
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String item;

    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.RECEIVED;
    
    private String customerName;
    
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime completedAt;

    public Order() {} // ✅ 기본 생성자

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getItem() { return item; }
    public void setItem(String item) { this.item = item; }

    public OrderStatus getStatus() { return status; }
    public void setStatus(OrderStatus status) { 
        this.status = status;
        if (status == OrderStatus.COMPLETED) {
            this.completedAt = LocalDateTime.now();
        }
    }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }
}
