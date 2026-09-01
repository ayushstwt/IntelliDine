package com.hunger.saviour.portal.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDTO {
    private UUID orderId;
    private String username;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private OrderStatus orderStatus;
    private Double totalPrice;
    private String transactionId;
    private String orderJson;
}
