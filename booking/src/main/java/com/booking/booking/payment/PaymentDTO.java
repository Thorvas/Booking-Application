package com.booking.booking.payment;

import lombok.Data;

@Data
public class PaymentDTO {

    private Long id;
    private Long bookingId;
    private PaymentStatus paymentStatus;
    private Long amount;
}
