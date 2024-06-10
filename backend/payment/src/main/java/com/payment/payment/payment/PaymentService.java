package com.payment.payment.payment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.payment.payment.booking.BookingModel;
import com.payment.payment.booking.BookingNotFoundException;
import com.payment.payment.booking.BookingRepository;
import com.payment.payment.booking.BookingStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    public List<PaymentDTO> getAllPayments() {

        return paymentRepository.findAll().stream()
                .map(paymentModel -> convertToDTO(paymentModel))
                .toList();
    }

    public PaymentDTO getPayment(Long id) {

        PaymentModel payment = paymentRepository.findById(id).orElseThrow(() -> new PaymentNotFoundException("Payment not found."));

        return convertToDTO(payment);
    }

    public PaymentDTO deletePayment(Long id) {

        PaymentModel payment = paymentRepository.findById(id).orElseThrow(() -> new PaymentNotFoundException("Payment not found."));

        paymentRepository.delete(payment);

        return convertToDTO(payment);
    }

    public PaymentDTO createPayment(PaymentDTO paymentDTO) {

        PaymentModel paymentModel = convertToPayment(paymentDTO);
        BookingModel booking = bookingRepository.findById(paymentModel.getBookingId()).orElseThrow(() -> new BookingNotFoundException("No such booking exists!"));

        booking.setBookingStatus(PaymentStatus.ACCEPTED.equals(paymentModel.getPaymentStatus()) ? BookingStatus.CONFIRMED : BookingStatus.REJECTED);

        paymentRepository.save(paymentModel);
        propagatePaymentCreated(paymentModel);

        return convertToDTO(paymentModel);

    }

    public void propagatePaymentCreated(PaymentModel model) {
        propagate(model, "payment_processed");
    }

    public void propagate(PaymentModel payment, String topic) {
        try {
            String request = objectMapper.writeValueAsString(payment);
            kafkaTemplate.send(topic, request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public PaymentDTO updatePayment(PaymentDTO paymentDTO, Long id) {

        PaymentModel payment = paymentRepository.findById(id).orElseThrow( () -> new PaymentNotFoundException("Payment not found."));

        payment.setAmount(paymentDTO.getAmount());
        payment.setPaymentStatus(paymentDTO.getPaymentStatus());
        payment.setBookingId(paymentDTO.getBookingId());

        paymentRepository.save(payment);

        return convertToDTO(payment);
    }

    private PaymentDTO convertToDTO(PaymentModel paymentModel) {

        PaymentDTO paymentDTO = new PaymentDTO();

        paymentDTO.setId(paymentModel.getId());
        paymentDTO.setAmount(paymentModel.getAmount());
        paymentDTO.setPaymentStatus(paymentModel.getPaymentStatus());
        paymentDTO.setBookingId(paymentModel.getBookingId());

        return paymentDTO;
    }

    private PaymentModel convertToPayment(PaymentDTO paymentDTO) {

        PaymentModel paymentModel = new PaymentModel();

        paymentModel.setBookingId(paymentDTO.getBookingId());
        paymentModel.setAmount(paymentDTO.getAmount());
        paymentModel.setPaymentStatus(paymentDTO.getPaymentStatus());

        return paymentModel;
    }
}
