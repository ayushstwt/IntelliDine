package com.hunger.saviour.portal.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Table(name = "payments")
public class PaymentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID paymentId;
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;
    @Column(name = "txn_date_time")
    private LocalDateTime txnDateAndTime;
    @Column(name = "username")
    private String username;
    @Column(name = "txn_id")
    private String transactionId;
}