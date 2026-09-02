package com.intellidine.portal.services;

import com.intellidine.portal.entities.OrderEntity;

import java.util.List;

public interface OrderService {
    void processOrder(OrderEntity orderEntity);
    List<OrderEntity> getOrdersByUsername(String username);
}
