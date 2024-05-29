package com.notification.notification.exceptionHandler;

import com.notification.notification.notification.NotificationNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotificationNotFoundException.class)
    public ResponseEntity<?> paymentNotFoundExceptionHandler(NotificationNotFoundException ex, WebRequest request) {
        ExceptionDetails exceptionDetails = new ExceptionDetails(new Date(), ex.getMessage(), request.getDescription(false), HttpStatus.NOT_FOUND.toString());

        return new ResponseEntity<>(exceptionDetails, HttpStatus.NOT_FOUND);
    }

}
