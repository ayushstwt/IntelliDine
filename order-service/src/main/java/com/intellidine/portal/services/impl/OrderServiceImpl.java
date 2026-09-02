package com.intellidine.portal.services.impl;

import com.intellidine.portal.entities.OrderEntity;
import com.intellidine.portal.repositories.OrderRepository;
import com.intellidine.portal.services.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Override
    @Transactional
    public void processOrder(OrderEntity orderEntity) {
        log.info("Persisting order to database for user: {}", orderEntity.getUsername());
        this.orderRepository.save(orderEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderEntity> getOrdersByUsername(String username) {
        log.info("Fetching orders for username: {}", username);
        return this.orderRepository.findByUsername(username);
    }
}
