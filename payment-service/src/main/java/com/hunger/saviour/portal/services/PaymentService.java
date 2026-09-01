package com.hunger.saviour.portal.services;

import com.hunger.saviour.portal.dtos.OrderDTO;
import com.hunger.saviour.portal.entities.PaymentEntity;

public interface PaymentService {

    PaymentEntity processPayment(OrderDTO orderDTO);
}
