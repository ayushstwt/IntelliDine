package com.intellidine.portal.services.impl;

import com.intellidine.portal.dtos.OrderDTO;
import com.intellidine.portal.entities.PaymentEntity;
import com.intellidine.portal.entities.PaymentStatus;
import com.intellidine.portal.kafka.PaymentServiceKafkaPublisher;
import com.intellidine.portal.repositories.PaymentRepository;
import com.intellidine.portal.services.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentServiceKafkaPublisher paymentServiceKafkaPublisher;

    @Override
    @Transactional
    public PaymentEntity processPayment(OrderDTO orderDTO) {
        log.info("Processing payment for user: {}, transaction: {}", orderDTO.getUsername(), orderDTO.getTransactionId());
        PaymentEntity paymentEntity = PaymentEntity.builder()
                .paymentStatus(PaymentStatus.PAYMENT_SUCCESS)
                .transactionId(orderDTO.getTransactionId())
                .username(orderDTO.getUsername())
                .txnDateAndTime(LocalDateTime.now())
                .build();
        PaymentEntity savedPayment = this.paymentRepository.save(paymentEntity);
        this.paymentServiceKafkaPublisher.publishOrderDetailsToOrdersTopic(orderDTO);
        return savedPayment;
    }
}
