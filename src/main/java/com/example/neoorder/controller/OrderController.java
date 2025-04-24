package com.example.neoorder.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @GetMapping
    public List<Map<String, String>> getOrders() {
        return List.of(
            Map.of("id", "1", "item", "Brick"),
            Map.of("id", "2", "item", "Laser")
        );
    }
}
