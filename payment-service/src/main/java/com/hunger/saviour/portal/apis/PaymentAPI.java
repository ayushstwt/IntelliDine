package com.hunger.saviour.portal.apis;

import com.hunger.saviour.common.dto.ApiResponse;
import com.hunger.saviour.common.filter.TraceIdFilter;
import com.hunger.saviour.portal.dtos.OrderDTO;
import com.hunger.saviour.portal.entities.PaymentEntity;
import com.hunger.saviour.portal.services.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("payments")
@RequiredArgsConstructor
@Slf4j
public class PaymentAPI {

    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<ApiResponse<PaymentEntity>> createPayment(@Valid @RequestBody OrderDTO orderDTO, HttpServletRequest request) {
        log.info("Received request to process payment for user: {}", orderDTO.getUsername());
        PaymentEntity paymentEntity = this.paymentService.processPayment(orderDTO);
        return new ResponseEntity<>(
                ApiResponse.ok(paymentEntity, "Payment processed successfully and order dispatched", request.getRequestURI(), TraceIdFilter.getTraceId()),
                HttpStatus.CREATED
        );
    }
}

