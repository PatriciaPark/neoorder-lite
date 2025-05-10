package com.example.neoorder.controller;

import com.example.neoorder.model.Order;
import com.example.neoorder.model.OrderStatus;
import com.example.neoorder.repository.OrderRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

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
    @Operation(
        summary = "주문 목록 조회 (Get Orders)",
        description = "고객명, 상태, 아이템명으로 검색 및 페이지네이션이 가능한 주문 목록을 조회합니다.\nSearch and paginate orders by customer name, status, or item.",
        parameters = {
            @Parameter(name = "customerName", description = "고객명 (Customer Name)", example = "홍길동"),
            @Parameter(name = "status", description = "주문 상태 (Order Status)", example = "RECEIVED"),
            @Parameter(name = "item", description = "상품명 (Item Name)", example = "노트북"),
            @Parameter(name = "page", description = "페이지 번호 (Page number)", example = "0"),
            @Parameter(name = "size", description = "페이지 크기 (Page size)", example = "10")
        }
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "주문 목록 조회 성공 (Success)",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = org.springframework.data.domain.Page.class),
                examples = @ExampleObject(value = "{\n  \"content\": [\n    {\n      \"id\": 1,\n      \"item\": \"노트북\",\n      \"status\": \"RECEIVED\",\n      \"customerName\": \"홍길동\",\n      \"createdAt\": \"2024-06-01T12:00:00\"\n    }\n  ],\n  \"totalElements\": 1,\n  \"totalPages\": 1,\n  \"size\": 10,\n  \"number\": 0\n}")))
    })
    @GetMapping
    public Page<Order> getOrders(
        @RequestParam(required = false) String customerName,
        @RequestParam(required = false) String status,
        @RequestParam(required = false) String item,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
    ) {
        PageRequest pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));

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
    @Operation(
        summary = "주문 단건 조회 (Get Order by ID)",
        description = "주문 ID로 단일 주문을 조회합니다.\nGet a single order by its ID.",
        parameters = {
            @Parameter(name = "id", description = "주문 ID (Order ID)", example = "1")
        }
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "주문 조회 성공 (Success)",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = com.example.neoorder.model.Order.class),
                examples = @ExampleObject(value = "{\n  \"id\": 1,\n  \"item\": \"노트북\",\n  \"status\": \"RECEIVED\",\n  \"customerName\": \"홍길동\",\n  \"createdAt\": \"2024-06-01T12:00:00\"\n}")))
    })
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
    @Operation(
        summary = "주문 생성 (Create Order)",
        description = "새로운 주문을 생성합니다.\nCreate a new order.",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "주문 정보 (Order data)",
            required = true,
            content = @Content(
                schema = @Schema(implementation = com.example.neoorder.model.Order.class),
                examples = @ExampleObject(value = "{\n  \"item\": \"노트북\",\n  \"customerName\": \"홍길동\"\n}")
            )
        )
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "주문 생성 성공 (Success)",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = com.example.neoorder.model.Order.class),
                examples = @ExampleObject(value = "{\n  \"id\": 2,\n  \"item\": \"노트북\",\n  \"status\": \"RECEIVED\",\n  \"customerName\": \"홍길동\",\n  \"createdAt\": \"2024-06-01T12:01:00\"\n}")))
    })
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
    @Operation(
        summary = "주문 상태 변경 (Update Order Status)",
        description = "주문 상태를 다음 단계로 변경합니다. (RECEIVED→SHIPPING→COMPLETED)\nUpdate order status to the next step.",
        parameters = {
            @Parameter(name = "id", description = "주문 ID (Order ID)", example = "1")
        }
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "상태 변경 성공 (Success)",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = com.example.neoorder.model.Order.class),
                examples = @ExampleObject(value = "{\n  \"id\": 1,\n  \"status\": \"SHIPPING\"\n}")))
    })
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
    @Operation(
        summary = "주문 삭제 (Delete Order)",
        description = "주문을 삭제합니다.\nDelete an order.",
        parameters = {
            @Parameter(name = "id", description = "주문 ID (Order ID)", example = "1")
        }
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "주문 삭제 성공 (Success)")
    })
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
    @Operation(
        summary = "주문 취소 (Cancel Order)",
        description = "주문을 취소(상태를 CANCELLED로 변경)합니다.\nCancel an order (set status to CANCELLED).",
        parameters = {
            @Parameter(name = "id", description = "주문 ID (Order ID)", example = "1")
        }
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "주문 취소 성공 (Success)",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = com.example.neoorder.model.Order.class),
                examples = @ExampleObject(value = "{\n  \"id\": 1,\n  \"status\": \"CANCELLED\"\n}")))
    })
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
    @Operation(
        summary = "상태별 주문 목록 조회 (Get Orders by Status)",
        description = "특정 상태의 주문 목록을 조회합니다.\nGet orders by status.",
        parameters = {
            @Parameter(name = "status", description = "주문 상태 (Order Status)", example = "RECEIVED")
        }
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "상태별 주문 목록 조회 성공 (Success)",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = com.example.neoorder.model.Order.class, type = "array"),
                examples = @ExampleObject(value = "[{\n  \"id\": 1,\n  \"status\": \"RECEIVED\"\n}]")
            )
        )
    })
    @GetMapping("/status/{status}")
    public List<Order> getOrdersByStatus(@PathVariable OrderStatus status) {
        return orderRepository.findByStatus(status);
    }

    /**
     * 주문 수정
     */
    @Operation(
        summary = "주문 수정 (Update Order)",
        description = "주문 정보를 수정합니다. (취소/완료된 주문은 수정 불가)\nUpdate order info (cannot update cancelled/completed orders).",
        parameters = {
            @Parameter(name = "id", description = "주문 ID (Order ID)", example = "1")
        },
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "수정할 주문 정보 (Order update data)",
            required = true,
            content = @Content(
                schema = @Schema(implementation = com.example.neoorder.model.Order.class),
                examples = @ExampleObject(value = "{\n  \"item\": \"모니터\",\n  \"customerName\": \"김철수\"\n}")
            )
        )
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "주문 수정 성공 (Success)",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = com.example.neoorder.model.Order.class),
                examples = @ExampleObject(value = "{\n  \"id\": 1,\n  \"item\": \"모니터\",\n  \"customerName\": \"김철수\",\n  \"status\": \"RECEIVED\"\n}")))
    })
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
    @Operation(
        summary = "주문 복제 (Duplicate Order)",
        description = "기존 주문을 복제하여 새 주문을 생성합니다.\nDuplicate an order.",
        parameters = {
            @Parameter(name = "id", description = "주문 ID (Order ID)", example = "1")
        }
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "주문 복제 성공 (Success)",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = com.example.neoorder.model.Order.class),
                examples = @ExampleObject(value = "{\n  \"id\": 3,\n  \"item\": \"노트북\",\n  \"customerName\": \"홍길동\",\n  \"status\": \"RECEIVED\"\n}")))
    })
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
