package com.example.dto;

import lombok.Data;

@Data
public class BookingRequestDTO {

    private Long bookingId;
    private Long eventId;
    private Long userId;

}
