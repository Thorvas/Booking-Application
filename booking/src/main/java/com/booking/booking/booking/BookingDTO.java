package com.booking.booking.booking;

import lombok.Data;

@Data
public class BookingDTO {

    private Long id;
    private Long eventId;
    private Long userId;
    private String date;

}
