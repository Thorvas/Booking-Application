package com.event.event.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
public class EventDTO {

    private Long id;
    private Long authorId;
    private String name;
    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime date;
}
