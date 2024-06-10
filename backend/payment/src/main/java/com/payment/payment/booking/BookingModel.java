package com.payment.payment.booking;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class BookingModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long eventId;
    private Long ownerId;
    private Integer ticketAmount;
    private Integer totalPrice;
    @Enumerated(EnumType.STRING)
    private BookingStatus bookingStatus;

}
