package com.example.bookmyshow_be.DTOs.PaymentDTOs;

import com.example.bookmyshow_be.Models.Payment;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentDTO {
    private Long paymentId;
    private double amount;
    private String paymentStatus;
    private String paymentMethod;

    public static PaymentDTO convertPaymentToDTO(Payment payment) {
        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setPaymentId(payment.getPaymentId());
        paymentDTO.setAmount(payment.getTotalAmount());
        paymentDTO.setPaymentStatus(payment.getPaymentStatus().name());
        if (payment.getPaymentType() != null) {
            paymentDTO.setPaymentMethod(payment.getPaymentType().name());
        }
        return paymentDTO;
    }
}
