package com.booking.booking.booking;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/booking")
public class BookingController {

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getBookings(){

        return "Get all my bookings.";
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getBooking(@PathVariable Long id) {

        return "Return specific booking.";
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public String createBooking(@RequestBody String booking) {

        return "Create specific booking";
    }
}