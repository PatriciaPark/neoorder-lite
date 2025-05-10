package com.example.neoorder.controller;

import com.example.neoorder.model.Order;
import com.example.neoorder.model.OrderStatus;
import com.example.neoorder.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/statistics")
@CrossOrigin(origins = "*")
public class StatisticsController {

    @Autowired
    private OrderRepository orderRepository;

    @Operation(
        summary = "주문 통계 조회 (Get Order Statistics)",
        description = "주문 통계를 조회합니다. 기간(시작/종료일) 지정 가능.\nGet order statistics. You can specify start/end date.",
        parameters = {
            @Parameter(name = "startDate", description = "시작일 (ISO-8601, Start date)", example = "2024-06-01T00:00:00"),
            @Parameter(name = "endDate", description = "종료일 (ISO-8601, End date)", example = "2024-06-30T23:59:59")
        }
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "통계 조회 성공 (Success)",
            content = @Content(mediaType = "application/json",
                examples = @ExampleObject(value = "{\n  \"totalOrders\": 10,\n  \"statusStats\": {\n    \"RECEIVED\": 2,\n    \"SHIPPING\": 3,\n    \"COMPLETED\": 4,\n    \"CANCELLED\": 1\n  },\n  \"popularItems\": [\n    {\"item\": \"노트북\", \"count\": 5},\n    {\"item\": \"모니터\", \"count\": 3}\n  ],\n  \"avgProcessingTime\": 120\n}")))
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getStatistics(
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate) {
        
        try {
            LocalDateTime startDateTime;
            LocalDateTime endDateTime;
            
            // 날짜 파싱 및 변환
            if (startDate != null && endDate != null) {
                // ISO 형식의 날짜 문자열을 LocalDateTime으로 변환
                DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
                startDateTime = LocalDateTime.parse(startDate, formatter);
                endDateTime = LocalDateTime.parse(endDate, formatter);
            } else {
                // 날짜가 없는 경우 기본값 설정 (최근 30일)
                endDateTime = LocalDateTime.now();
                startDateTime = endDateTime.minusDays(30);
            }

            // 기간 내 모든 주문 조회
            List<Order> orders = orderRepository.findByCreatedAtBetween(startDateTime, endDateTime);

            // 전체 주문 수
            int totalOrders = orders.size();

            // 상태별 주문 수 계산
            Map<String, Long> statusStats = new HashMap<>();
            for (OrderStatus status : OrderStatus.values()) {
                statusStats.put(status.name(), 0L);
            }
            
            orders.forEach(order -> {
                String status = order.getStatus().name();
                statusStats.put(status, statusStats.get(status) + 1);
            });

            // 인기 상품 TOP 5 계산 (CANCELLED 상태 제외)
            List<Map<String, Object>> popularItems = orders.stream()
                    .filter(order -> order.getStatus() != OrderStatus.CANCELLED)
                    .collect(Collectors.groupingBy(Order::getItem, Collectors.counting()))
                    .entrySet().stream()
                    .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                    .limit(5)
                    .map(entry -> {
                        Map<String, Object> item = new HashMap<>();
                        item.put("item", entry.getKey());
                        item.put("count", entry.getValue());
                        return item;
                    })
                    .collect(Collectors.toList());

            // 평균 처리 시간 계산 (완료된 주문만 대상)
            double avgProcessingTime = orders.stream()
                    .filter(order -> order.getStatus() == OrderStatus.COMPLETED && order.getCompletedAt() != null)
                    .mapToLong(order -> {
                        LocalDateTime completedAt = order.getCompletedAt();
                        LocalDateTime createdAt = order.getCreatedAt();
                        return java.time.Duration.between(createdAt, completedAt).toMinutes();
                    })
                    .average()
                    .orElse(0.0);

            // 응답 데이터 구성
            Map<String, Object> response = new HashMap<>();
            response.put("totalOrders", totalOrders);
            response.put("statusStats", statusStats);
            response.put("popularItems", popularItems);
            response.put("avgProcessingTime", Math.round(avgProcessingTime)); // 반올림하여 정수로 변환

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(response);
            
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "통계 데이터를 처리하는 중 오류가 발생했습니다: " + e.getMessage());
            return ResponseEntity.badRequest()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(error);
        }
    }
} 