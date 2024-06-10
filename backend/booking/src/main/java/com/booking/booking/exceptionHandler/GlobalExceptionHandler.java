package com.booking.booking.exceptionHandler;

import com.booking.booking.booking.BookingNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BookingNotFoundException.class)
    public ResponseEntity<?> bookingNotFoundExceptionHandler(BookingNotFoundException ex, WebRequest request) {
        ExceptionDetails exceptionDetails = new ExceptionDetails(new Date(), ex.getMessage(), request.getDescription(false), HttpStatus.NOT_FOUND.toString());

        return new ResponseEntity<>(exceptionDetails, HttpStatus.NOT_FOUND);
    }

}
