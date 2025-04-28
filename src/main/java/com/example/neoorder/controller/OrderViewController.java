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

    // 주문 목록 페이지 렌더링
    @GetMapping("/orders")
    public String getOrders(Model model) {
        List<Order> orders = orderRepository.findAll();
        model.addAttribute("orders", orders); // 주문 리스트를 모델에 담음
        return "orders"; // 뷰 이름 반환 (예: orders.jsp 또는 orders.html)
    }

    // 주문 상태 업데이트 (RECEIVED -> SHIPPING -> COMPLETED)
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
        return "redirect:/orders"; // 상태 변경 후 목록 페이지로 리디렉션
    }

    // 주문 삭제 처리
    @PostMapping("/orders/{id}/delete")
    public String deleteOrder(@PathVariable Long id) {
        if (!orderRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "주문이 존재하지 않습니다.");
        }
        orderRepository.deleteById(id);
        return "redirect:/orders"; // 삭제 후 목록 페이지로 리디렉션
    }

    // 신규 주문 입력 폼 렌더링
    @GetMapping("/orders/new")
    public String showCreateForm() {
        return "create-order"; // 뷰 이름 반환 (예: create-order.jsp)
    }

    // 주문 생성 처리 (Form 기반)
    // ⚠️ 이 메소드는 customerName을 저장하지 않기 때문에 REST 방식과 충돌 가능성 있음
    @PostMapping("/orders")
    public String createOrder(@RequestParam String item) {
        Order order = new Order();
        order.setItem(item);
        order.setStatus(OrderStatus.RECEIVED);
        orderRepository.save(order);
        return "redirect:/orders"; // 생성 후 목록 페이지로 리디렉션
    }
}