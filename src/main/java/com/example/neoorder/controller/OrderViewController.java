package com.example.neoorder.controller;

import com.example.neoorder.model.Order;
import com.example.neoorder.model.OrderStatus;
import com.example.neoorder.repository.OrderRepository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Controller
public class OrderViewController {

    private final OrderRepository orderRepository;

    public OrderViewController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @GetMapping("/orders")
    public String getOrders(Model model) {
        List<Order> orders = orderRepository.findAll();
        model.addAttribute("orders", orders);
        return "orders";
    }

    @PostMapping("/orders/{id}/status")
    public String updateStatus(@PathVariable Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        switch (order.getStatus()) {
            case RECEIVED -> order.setStatus(OrderStatus.SHIPPING);
            case SHIPPING -> order.setStatus(OrderStatus.COMPLETED);
            case COMPLETED -> throw new IllegalStateException("이미 완료된 주문입니다.");
        }
        orderRepository.save(order);
        return "redirect:/orders";
    }

    @PostMapping("/orders/{id}/delete")
    public String deleteOrder(@PathVariable Long id) {
        if (!orderRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "주문이 존재하지 않습니다.");
        }
        orderRepository.deleteById(id);
        return "redirect:/orders";
    }

    @GetMapping("/orders/new")
    public String showCreateForm() {
        return "create-order"; // create-order.jsp
    }

    @PostMapping("/orders")
    public String createOrder(@RequestParam String item) {
        Order order = new Order();
        order.setItem(item);
        order.setStatus(OrderStatus.RECEIVED);
        orderRepository.save(order);
        return "redirect:/orders";
    }

}
