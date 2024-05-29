package com.booking.booking.booking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/booking")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<BookingDTO>> getBookings(){

        List<BookingDTO> booking = bookingService.getAllBookings();

        return new ResponseEntity<>(booking, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookingDTO> getBooking(@PathVariable Long id) {

        BookingDTO booking = bookingService.getBooking(id);

        return new ResponseEntity<>(booking, HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookingDTO> createBooking(@RequestBody BookingDTO bookingDTO) {

        BookingDTO booking = bookingService.createBooking(bookingDTO);

        return new ResponseEntity<>(booking, HttpStatus.OK);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookingDTO> updateBooking(@PathVariable Long id, @RequestBody BookingDTO bookingDTO) {

        BookingDTO booking = bookingService.updateBooking(bookingDTO, id);

        return new ResponseEntity<>(booking, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookingDTO> deleteBooking(@PathVariable Long id) {

        BookingDTO booking = bookingService.deleteBooking(id);

        return new ResponseEntity<>(booking, HttpStatus.OK);
    }
}