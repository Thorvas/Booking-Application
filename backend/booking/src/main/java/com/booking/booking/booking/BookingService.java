package com.booking.booking.booking;

import com.booking.booking.event.Event;
import com.booking.booking.event.EventDTO;
import com.booking.booking.event.EventNotFoundException;
import com.booking.booking.event.EventRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public List<BookingDTO> getAllBookings() {

        return bookingRepository.findAll().stream()
                .map(bookingModel -> convertToDTO(bookingModel))
                .toList();
    }

    public BookingDTO updateAndConvertBooking(BookingModel booking, BookingDTO bookingDTO) {

        BeanUtils.copyProperties(bookingDTO, booking, "id");

        bookingRepository.save(booking);
        propagateBookingUpdated(booking);
        return convertToDTO(booking);
    }

    public BookingDTO deleteAndConvertBooking(BookingModel booking) {

        bookingRepository.delete(booking);
        propagateBookingDeleted(booking);

        return convertToDTO(booking);
    }

    public BookingDTO throwException(String message) {
        throw new RuntimeException(message);
    }

    public BookingDTO getBooking(Long id) {

        BookingModel booking = bookingRepository.findById(id).orElseThrow(() -> new BookingNotFoundException("Booking not found."));

        return convertToDTO(booking);
    }

    public BookingDTO deleteBooking(Long id, Jwt jwt) {

        BookingModel booking = bookingRepository.findById(id).orElseThrow(() -> new BookingNotFoundException("Booking not found."));

        return booking.getOwnerId().equals(Long.valueOf(jwt.getSubject())) ?
                deleteAndConvertBooking(booking) : throwException("You are not an owner of booking.");
    }

    public EventDTO getEventForBooking(Long bookingId) {

        BookingModel booking = bookingRepository.findById(bookingId).orElseThrow(() -> new BookingNotFoundException("Booking not found."));

        Event event = eventRepository.findById(booking.getEventId()).orElseThrow(() -> new EventNotFoundException("There is no such event in database."));

        EventDTO eventDTO = new EventDTO();

        BeanUtils.copyProperties(event, eventDTO);

        return eventDTO;
    }

    public BookingDTO createBooking(BookingDTO bookingDTO, Jwt jwt) {

        BookingModel bookingModel = convertToBooking(bookingDTO);
        Event event = eventRepository.findById(bookingModel.getEventId()).orElseThrow(() -> new EventNotFoundException("No such event exists"));

        bookingModel.setBookingStatus(BookingStatus.PENDING);
        bookingModel.setOwnerId(Long.valueOf(jwt.getSubject()));
        bookingModel.setEventId(event.getId());

        bookingModel = bookingRepository.save(bookingModel);
        propagateBookingCreated(bookingModel);

        return convertToDTO(bookingModel);

    }

    public BookingDTO updateBooking(BookingDTO bookingDTO, Long id, Jwt jwt) {

        BookingModel booking = bookingRepository.findById(id).orElseThrow(() -> new BookingNotFoundException("Booking not found."));

        return booking.getOwnerId().equals(Long.valueOf(jwt.getSubject())) ?
                updateAndConvertBooking(booking, bookingDTO) : throwException("You are not owner of the booking.");
    }

    public void propagateBookingCreated(BookingModel booking) {
        propagate(booking, "booking_created");
    }

    public void propagateBookingUpdated(BookingModel booking) {
        propagate(booking, "booking_updated");
    }

    public void propagateBookingDeleted(BookingModel booking) {
        propagate(booking, "booking_deleted");
    }

    private void propagate(BookingModel booking, String topic) {

        try {
            String request = objectMapper.writeValueAsString(convertToDTO(booking));
            kafkaTemplate.send(topic, request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private BookingDTO convertToDTO(BookingModel bookingModel) {

        BookingDTO bookingDTO = new BookingDTO();

        bookingDTO.setId(bookingModel.getId());
        bookingDTO.setBookingStatus(bookingModel.getBookingStatus());
        bookingDTO.setEventId(bookingModel.getEventId());
        bookingDTO.setOwnerId(bookingModel.getOwnerId());
        bookingDTO.setTotalPrice(bookingModel.getTotalPrice());
        bookingDTO.setTicketAmount(bookingModel.getTicketAmount());

        return bookingDTO;
    }

    private BookingModel convertToBooking(BookingDTO bookingDTO) {

        BookingModel bookingModel = new BookingModel();

        bookingModel.setEventId(bookingDTO.getEventId());
        bookingModel.setBookingStatus(bookingDTO.getBookingStatus());
        bookingModel.setOwnerId(bookingDTO.getOwnerId());
        bookingModel.setTotalPrice(bookingDTO.getTotalPrice());
        bookingModel.setTicketAmount(bookingDTO.getTicketAmount());

        return bookingModel;
    }
}
