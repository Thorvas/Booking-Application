package com.booking.booking.booking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

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

    public BookingDTO createBooking(BookingDTO bookingDTO) {

        BookingModel bookingModel = convertToBooking(bookingDTO);

        bookingRepository.save(bookingModel);

        return convertToDTO(bookingModel);

    }

    public BookingDTO updateBooking(BookingDTO bookingDTO, Long id) {

        BookingModel booking = bookingRepository.findById(id).orElseThrow( () -> new BookingNotFoundException("Booking not found."));

        booking.setDate(bookingDTO.getDate());
        booking.setName(bookingDTO.getName());

        bookingRepository.save(booking);

        return convertToDTO(booking);
    }

    private BookingDTO convertToDTO(BookingModel bookingModel) {

        BookingDTO bookingDTO = new BookingDTO();

        bookingDTO.setId(bookingModel.getId());
        bookingDTO.setDate(bookingModel.getDate());
        bookingDTO.setName(bookingModel.getName());

        return bookingDTO;
    }

    private BookingModel convertToBooking(BookingDTO bookingDTO) {

        BookingModel bookingModel = new BookingModel();

        bookingModel.setDate(bookingDTO.getDate());
        bookingModel.setName(bookingDTO.getName());

        return bookingModel;
    }
}
