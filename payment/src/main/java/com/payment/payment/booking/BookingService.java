package com.payment.payment.booking;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class BookingService {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BookingRepository bookingRepository;

    @KafkaListener(topics = "booking_created", groupId = "payment_listeners")
    public void createBooking(String request) {
        try {
            BookingModel booking = objectMapper.readValue(request, BookingModel.class);
            bookingRepository.save(booking);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @KafkaListener(topics = "booking_deleted", groupId = "payment_listeners")
    public void deleteBooking(String request) {
        try {
            BookingModel booking = objectMapper.readValue(request, BookingModel.class);
            bookingRepository.delete(booking);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @KafkaListener(topics = "booking_updated", groupId = "payment_listeners")
    public void updateBooking(String request) {
        try {
            BookingModel booking = objectMapper.readValue(request, BookingModel.class);
            BookingModel bookingDTO = bookingRepository.findById(booking.getId()).orElseThrow(() -> new BookingNotFoundException("Booking not found!"));

            BeanUtils.copyProperties(booking, bookingDTO, "id");

            bookingRepository.save(bookingDTO);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initiatePayment(BookingModel booking) {

    }
}
