package com.event.event.event;

import lombok.Data;

import java.time.LocalDate;

@Data
public class EventDTO {

    private Long id;
    private Long authorId;
    private String name;
    private LocalDate date;
}
