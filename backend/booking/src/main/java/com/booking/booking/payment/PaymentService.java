package com.booking.booking.payment;

import com.booking.booking.booking.BookingModel;
import com.booking.booking.booking.BookingNotFoundException;
import com.booking.booking.booking.BookingRepository;
import com.booking.booking.booking.BookingStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @KafkaListener(topics = "payment_processed", groupId = "booking_listeners")
    public void processPayment(String request) {
        try {
            PaymentDTO paymentDTO = objectMapper.readValue(request, PaymentDTO.class);

            BookingModel bookingModel = bookingRepository.findById(paymentDTO.getBookingId()).orElseThrow(() -> new BookingNotFoundException("No such booking found."));

            bookingModel.setBookingStatus(PaymentStatus.ACCEPTED.equals(paymentDTO.getPaymentStatus()) ? BookingStatus.CONFIRMED : BookingStatus.REJECTED);
            bookingRepository.save(bookingModel);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
