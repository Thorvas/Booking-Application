package com.booking.booking.booking;

import com.booking.booking.event.Event;
import com.booking.booking.event.EventDTO;
import com.booking.booking.event.EventRepository;
import com.example.dto.BookingRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private EventRepository eventRepository;

    public List<BookingDTO> getAllBookings() {

        return bookingRepository.findAll().stream()
                .map(bookingModel -> convertToDTO(bookingModel))
                .toList();
    }

    public BookingDTO getBooking(Long id) {

        BookingModel booking = bookingRepository.findById(id).orElseThrow(() -> new BookingNotFoundException("Booking not found."));

        return convertToDTO(booking);
    }

    public BookingDTO deleteBooking(Long id) {

        BookingModel booking = bookingRepository.findById(id).orElseThrow(() -> new BookingNotFoundException("Booking not found."));

        bookingRepository.delete(booking);

        return convertToDTO(booking);
    }

    public EventDTO getEventForBooking(Long bookingId) {

        BookingModel booking = bookingRepository.findById(bookingId).orElseThrow(() -> new BookingNotFoundException("Booking not found."));

        Event event = eventRepository.findById(booking.getEventId()).orElseThrow(() -> new RuntimeException("There is no such event in database."));

        EventDTO eventDTO = new EventDTO();

        eventDTO.setName(event.getName());
        eventDTO.setId(event.getId());
        eventDTO.setDate(event.getDate());

        return eventDTO;
    }

    public BookingDTO createBooking(BookingDTO bookingDTO) {

        BookingModel bookingModel = convertToBooking(bookingDTO);
        BookingRequestDTO requestDTO = new BookingRequestDTO();

        bookingModel = bookingRepository.save(bookingModel);

        requestDTO.setBookingId(bookingModel.getId());
        requestDTO.setEventId(bookingDTO.getEventId());

        return convertToDTO(bookingModel);

    }

    public BookingDTO updateBooking(BookingDTO bookingDTO, Long id) {

        BookingModel booking = bookingRepository.findById(id).orElseThrow( () -> new BookingNotFoundException("Booking not found."));

        bookingRepository.save(booking);

        return convertToDTO(booking);
    }

    private BookingDTO convertToDTO(BookingModel bookingModel) {

        BookingDTO bookingDTO = new BookingDTO();

        bookingDTO.setId(bookingModel.getId());
        bookingDTO.setEventId(bookingDTO.getEventId());

        return bookingDTO;
    }

    private BookingModel convertToBooking(BookingDTO bookingDTO) {

        BookingModel bookingModel = new BookingModel();

        bookingModel.setEventId(bookingDTO.getEventId());

        return bookingModel;
    }
}
