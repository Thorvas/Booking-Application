package com.booking.booking.booking;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class BookingModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String date;

}
