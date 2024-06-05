package com.booking.booking.event;

import lombok.Data;

import java.time.LocalDate;

@Data
public class EventDTO {

    private Long id;
    private String name;
    private LocalDate date;
}
