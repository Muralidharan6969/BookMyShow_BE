package com.example.bookmyshow_be.Services.Payment;

import com.example.bookmyshow_be.Models.Payment;
import com.example.bookmyshow_be.Repositories.PaymentRepository;
import com.example.bookmyshow_be.Utils.ENUMS.PaymentStatus;
import org.springframework.stereotype.Service;

@Service("customPaymentService")
public class CustomPaymentService implements PaymentService{
    private PaymentRepository paymentRepository;

    public CustomPaymentService(PaymentRepository paymentRepository){
        this.paymentRepository = paymentRepository;
    }
    @Override
    public Payment processPayment(double amount, String currency, String paymentMethodId, String bookingId) {
        Payment payment = new Payment();
        payment.setRefNo(paymentMethodId);
        payment.setPaymentStatus(PaymentStatus.CONFIRMED);
        payment.setTotalAmount(amount);
        paymentRepository.save(payment);
        return payment;
    }
}
