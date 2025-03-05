package com.example.bookmyshow_be.Services.Payment;

import com.example.bookmyshow_be.Models.Payment;
import com.stripe.exception.StripeException;

import java.util.Map;

public interface PaymentService {
    Payment processPayment(double amount, String currency, String paymentMethodId, String bookingId) throws StripeException;
}
