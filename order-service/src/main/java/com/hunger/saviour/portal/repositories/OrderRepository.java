package com.hunger.saviour.portal.repositories;

import com.hunger.saviour.portal.entities.OrderEntity;
import com.hunger.saviour.portal.entities.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, UUID> {

    List<OrderEntity> findByOrderStatus(OrderStatus orderStatus);

    List<OrderEntity> findByUsername(String username);
}

