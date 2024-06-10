package com.payment.payment.payment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PaymentDTO>> getPayments(){

        List<PaymentDTO> payment = paymentService.getAllPayments();

        return new ResponseEntity<>(payment, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PaymentDTO> getPayment(@PathVariable Long id) {

        PaymentDTO payment = paymentService.getPayment(id);

        return new ResponseEntity<>(payment, HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PaymentDTO> createPayment(@RequestBody PaymentDTO paymentDTO) {

        PaymentDTO payment = paymentService.createPayment(paymentDTO);

        return new ResponseEntity<>(payment, HttpStatus.OK);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PaymentDTO> updatePayment(@PathVariable Long id, @RequestBody PaymentDTO paymentDTO) {

        PaymentDTO payment = paymentService.updatePayment(paymentDTO, id);

        return new ResponseEntity<>(payment, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PaymentDTO> deletePayment(@PathVariable Long id) {

        PaymentDTO payment = paymentService.deletePayment(id);

        return new ResponseEntity<>(payment, HttpStatus.OK);
    }
}
