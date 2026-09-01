package com.hunger.saviour.portal.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hunger.saviour.portal.dtos.OrderDTO;
import com.hunger.saviour.portal.entities.OrderEntity;
import com.hunger.saviour.portal.entities.OrderStatus;
import com.hunger.saviour.portal.services.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderServiceKafkaListener {

    private final ObjectMapper objectMapper;
    private final OrderService orderService;

    @KafkaListener(topics = "orders", groupId = "orders-group-1")
    public void pullOrderDetailsFromOrdersTopic(String orderJson){
        log.info("Received details {} from orders topic", orderJson);
        try {
            OrderDTO orderDTO = objectMapper.readValue(orderJson, OrderDTO.class);
            OrderEntity orderEntity = OrderEntity.builder()
                    .order_json(orderDTO.getOrderJson())
                    .orderStatus(OrderStatus.ORDER_PENDING)
                    .transactionId(orderDTO.getTransactionId())
                    .totalPrice(orderDTO.getTotalPrice())
                    .createdDate(LocalDateTime.now())
                    .updatedDate(LocalDateTime.now())
                    .build();
            this.orderService.processOrder(orderEntity);
        } catch (Exception e) {
            log.error("Error while receiving details from orders topic");
        }
    }
}
