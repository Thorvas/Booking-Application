package com.payment.payment.payment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    public List<PaymentDTO> getAllPayments() {

        return paymentRepository.findAll().stream()
                .map(paymentModel -> convertToDTO(paymentModel))
                .toList();
    }

    public PaymentDTO getPayment(Long id) {

        PaymentModel payment = paymentRepository.findById(id).orElseThrow(() -> new RuntimeException("Payment not found."));

        return convertToDTO(payment);
    }

    public PaymentDTO deletePayment(Long id) {

        PaymentModel payment = paymentRepository.findById(id).orElseThrow(() -> new RuntimeException("Payment not found."));

        paymentRepository.delete(payment);

        return convertToDTO(payment);
    }

    public PaymentDTO createPayment(PaymentDTO paymentDTO) {

        PaymentModel paymentModel = convertToPayment(paymentDTO);

        paymentRepository.save(paymentModel);

        return convertToDTO(paymentModel);

    }

    public PaymentDTO updatePayment(PaymentDTO paymentDTO, Long id) {

        PaymentModel payment = paymentRepository.findById(id).orElseThrow( () -> new RuntimeException("Payment not found."));

        payment.setAmount(paymentDTO.getAmount());
        payment.setTitle(paymentDTO.getTitle());

        paymentRepository.save(payment);

        return convertToDTO(payment);
    }

    private PaymentDTO convertToDTO(PaymentModel paymentModel) {

        PaymentDTO paymentDTO = new PaymentDTO();

        paymentDTO.setId(paymentModel.getId());
        paymentDTO.setAmount(paymentModel.getAmount());
        paymentDTO.setTitle(paymentModel.getTitle());

        return paymentDTO;
    }

    private PaymentModel convertToPayment(PaymentDTO paymentDTO) {

        PaymentModel paymentModel = new PaymentModel();

        paymentModel.setAmount(paymentDTO.getAmount());
        paymentModel.setTitle(paymentDTO.getTitle());

        return paymentModel;
    }
}
