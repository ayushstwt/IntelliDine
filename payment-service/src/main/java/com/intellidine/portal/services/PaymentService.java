package com.intellidine.portal.services;

import com.intellidine.portal.dtos.OrderDTO;
import com.intellidine.portal.entities.PaymentEntity;

public interface PaymentService {

    PaymentEntity processPayment(OrderDTO orderDTO);
}
