package com.payment.payment.payment;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class PaymentModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long bookingId;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    private Long ownerId;

    private Long amount;

}
