package com.hunger.saviour.portal.apis;

import com.hunger.saviour.common.dto.ApiResponse;
import com.hunger.saviour.common.filter.TraceIdFilter;
import com.hunger.saviour.portal.dtos.OrderDTO;
import com.hunger.saviour.portal.entities.OrderEntity;
import com.hunger.saviour.portal.services.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("orders")
@RequiredArgsConstructor
@Slf4j
public class OrderAPI {

    private final OrderService orderService;

    @GetMapping("/{username}")
    public ResponseEntity<ApiResponse<List<OrderDTO>>> getOrdersByUsername(@PathVariable String username, HttpServletRequest request) {
        log.info("Received request to fetch orders for username: {}", username);
        List<OrderEntity> entities = this.orderService.getOrdersByUsername(username);
        List<OrderDTO> dtos = entities.stream()
                .map(entity -> OrderDTO.builder()
                        .orderId(entity.getOrderId())
                        .username(entity.getUsername())
                        .orderStatus(entity.getOrderStatus())
                        .totalPrice(entity.getTotalPrice())
                        .transactionId(entity.getTransactionId())
                        .orderJson(entity.getOrder_json())
                        .createdDate(entity.getCreatedDate())
                        .updatedDate(entity.getUpdatedDate())
                        .build())
                .toList();

        return ResponseEntity.ok(
                ApiResponse.ok(dtos, "Orders retrieved successfully", request.getRequestURI(), TraceIdFilter.getTraceId())
        );
    }
}

