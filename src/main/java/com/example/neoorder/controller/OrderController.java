package com.example.neoorder.controller;

import com.example.neoorder.model.Order;
import com.example.neoorder.model.OrderStatus;
import com.example.neoorder.repository.OrderRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderRepository orderRepository;

    public OrderController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    /**
     * 주문 전체 목록 조회 (페이지네이션 및 검색 기능 포함)
     * - 고객명 또는 상태로 검색 가능
     * - 기본 10개 단위 페이지네이션
     */
    @GetMapping
    public Page<Order> getOrders(
        @RequestParam(required = false) String customerName,
        @RequestParam(required = false) String status,
        @RequestParam(required = false) String item,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
    ) {
        PageRequest pageable = PageRequest.of(page, size);

        // 모든 조건이 있는 경우
        if (item != null && !item.isEmpty() && customerName != null && !customerName.isEmpty() && status != null && !status.isEmpty()) {
            return orderRepository.findByItemContainingIgnoreCaseAndCustomerNameContainingIgnoreCaseAndStatus(
                item, customerName, OrderStatus.valueOf(status), pageable);
        }
        // 아이템명과 고객명만 있는 경우
        else if (item != null && !item.isEmpty() && customerName != null && !customerName.isEmpty()) {
            return orderRepository.findByItemContainingIgnoreCaseAndCustomerNameContainingIgnoreCase(
                item, customerName, pageable);
        }
        // 아이템명과 상태만 있는 경우
        else if (item != null && !item.isEmpty() && status != null && !status.isEmpty()) {
            return orderRepository.findByItemContainingIgnoreCaseAndStatus(
                item, OrderStatus.valueOf(status), pageable);
        }
        // 고객명과 상태만 있는 경우
        else if (customerName != null && !customerName.isEmpty() && status != null && !status.isEmpty()) {
            return orderRepository.findByCustomerNameContainingIgnoreCaseAndStatus(
                customerName, OrderStatus.valueOf(status), pageable);
        }
        // 아이템명만 있는 경우
        else if (item != null && !item.isEmpty()) {
            return orderRepository.findByItemContainingIgnoreCase(item, pageable);
        }
        // 고객명만 있는 경우
        else if (customerName != null && !customerName.isEmpty()) {
            return orderRepository.findByCustomerNameContainingIgnoreCase(customerName, pageable);
        }
        // 상태만 있는 경우
        else if (status != null && !status.isEmpty()) {
            return orderRepository.findByStatus(OrderStatus.valueOf(status), pageable);
        }
        // 검색 조건이 없는 경우
        else {
            return orderRepository.findAll(pageable);
        }
    }

    /**
     * 특정 ID로 주문 조회
     */
    @GetMapping("/{id}")
    public Order getOrder(@PathVariable Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    /**
     * 새로운 주문 생성
     * - ID는 무조건 null로 설정 (DB에서 자동 생성)
     * - 상태는 기본값 RECEIVED로 설정
     */
    @PostMapping
    public Order createOrder(@RequestBody Order order) {
        System.out.println("✔️ 등록된 주문: item = " + order.getItem() + ", customer = " + order.getCustomerName());
        order.setId(null);
        order.setStatus(OrderStatus.RECEIVED);
        return orderRepository.save(order);
    }

    /**
     * 주문 상태 업데이트
     * - RECEIVED -> SHIPPING -> COMPLETED 순차 변경
     */
    @PutMapping("/{id}/status")
    public Order updateOrderStatus(@PathVariable Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        switch (order.getStatus()) {
            case RECEIVED -> order.setStatus(OrderStatus.SHIPPING);
            case SHIPPING -> order.setStatus(OrderStatus.COMPLETED);
            case COMPLETED -> throw new IllegalStateException("이미 완료된 주문입니다.");
        }

        return orderRepository.save(order);
    }

    /**
     * 주문 삭제
     */
    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable Long id) {
        if (!orderRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "주문이 존재하지 않습니다.");
        }
        orderRepository.deleteById(id);
    }

    /**
     * 주문 취소 (상태를 CANCELLED로 변경)
     */
    @PutMapping("/{id}/cancel")
    public Order cancelOrder(@PathVariable Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        
        if (order.getStatus() == OrderStatus.COMPLETED) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "완료된 주문은 취소할 수 없습니다.");
        }
        
        if (order.getStatus() == OrderStatus.CANCELLED) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이미 취소된 주문입니다.");
        }
        
        order.setStatus(OrderStatus.CANCELLED);
        return orderRepository.save(order);
    }

    /**
     * 특정 상태별 주문 목록 조회
     */
    @GetMapping("/status/{status}")
    public List<Order> getOrdersByStatus(@PathVariable OrderStatus status) {
        return orderRepository.findByStatus(status);
    }

    /**
     * 주문 수정
     */
    @PutMapping("/{id}")
    public Order updateOrder(@PathVariable Long id, @RequestBody Order orderUpdate) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "주문을 찾을 수 없습니다."));
        
        // 취소된 주문은 수정 불가
        if (order.getStatus() == OrderStatus.CANCELLED) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "취소된 주문은 수정할 수 없습니다.");
        }
        
        // 완료된 주문은 수정 불가
        if (order.getStatus() == OrderStatus.COMPLETED) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "완료된 주문은 수정할 수 없습니다.");
        }

        order.setItem(orderUpdate.getItem());
        order.setCustomerName(orderUpdate.getCustomerName());
        
        return orderRepository.save(order);
    }

    /**
     * 주문 복제
     */
    @PostMapping("/{id}/duplicate")
    public Order duplicateOrder(@PathVariable Long id) {
        Order originalOrder = orderRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "주문을 찾을 수 없습니다."));
        
        Order newOrder = new Order();
        newOrder.setItem(originalOrder.getItem());
        newOrder.setCustomerName(originalOrder.getCustomerName());
        newOrder.setStatus(OrderStatus.RECEIVED); // 새 주문은 항상 RECEIVED 상태로 시작
        
        return orderRepository.save(newOrder);
    }

}
