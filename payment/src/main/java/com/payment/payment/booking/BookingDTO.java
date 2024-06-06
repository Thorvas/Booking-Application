package com.payment.payment.booking;

import lombok.Data;

@Data
public class BookingDTO {

    private Long id;
    private Long eventId;
    private Long ownerId;
    private Integer ticketAmount;
    private Integer totalPrice;
    private BookingStatus bookingStatus;

}
