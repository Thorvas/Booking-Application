package com.event.event.event;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/event")
public class EventController {

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getEvents(){

        return "Get all my bookings.";
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getBooking(@PathVariable Long id) {

        return "Return specific booking.";
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String createEvent(@RequestBody EventDTO bookingDTO) {

        return "Create specific booking";
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String updateEvent(@PathVariable Long id, @RequestBody EventDTO bookingDTO) {

        return "Update specific booking";
    }

    @DeleteMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String deleteEvent(@PathVariable Long id) {

        return "Delete specific booking";
    }
}
