package com.payment.payment.payment;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/payment")
public class PaymentController {

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getPayments(){

        return "Get all my bookings.";
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getPayment(@PathVariable Long id) {

        return "Return specific booking.";
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String createPayment(@RequestBody PaymentDTO paymentDTO) {

        return "Create specific booking";
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String updatePayment(@PathVariable Long id, @RequestBody PaymentDTO paymentDTO) {

        return "Update specific booking";
    }

    @DeleteMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String deletePayment(@PathVariable Long id) {

        return "Delete specific booking";
    }
}
