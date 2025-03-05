package com.example.bookmyshow_be.Services.Payment;

import com.example.bookmyshow_be.Exceptions.EntityNotFoundException;
import com.example.bookmyshow_be.Models.Payment;
import com.example.bookmyshow_be.Models.User;
import com.example.bookmyshow_be.Repositories.PaymentRepository;
import com.example.bookmyshow_be.Services.ShowService;
import com.example.bookmyshow_be.Utils.ENUMS.PaymentStatus;
import com.example.bookmyshow_be.Utils.ENUMS.PaymentType;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service("stripePaymentService")
public class StripePaymentService implements PaymentService{

    @Value("${stripe.secret.key}")
    private String stripeSecretKey;
    private ShowService showService;
    private PaymentRepository paymentRepository;


    @Autowired
    public StripePaymentService(@Value("${stripe.secret.key}") String stripeSecretKey, ShowService showService,
                                PaymentRepository paymentRepository) {
        this.stripeSecretKey = stripeSecretKey;
        Stripe.apiKey = stripeSecretKey;
        this.showService = showService;
        this.paymentRepository = paymentRepository;
    }


    public Map<String, Object> createPaymentIntent(User authenticatedUser, double amount, String currency,
                                                   String bookingId, List<Long> selectedSeats) throws StripeException {
        Stripe.apiKey = stripeSecretKey;

        showService.validateSession(authenticatedUser.getUserId(), selectedSeats);

        Map<String, Object> params = new HashMap<>();
        params.put("amount", (int) (amount * 100)); // Convert to cents
        params.put("currency", currency);
//        params.put("customer", authenticatedUser.getUserId());
        params.put("payment_method_types", List.of("card"));
        params.put("metadata", Map.of("bookingId", bookingId));
        params.put("description", "Export transaction for My personal project named SHOWGO which uses test keys from Stripe");

        Map<String, Object> address = new HashMap<>();
        address.put("line1", "123 Test Street");
        address.put("city", "Mumbai");
        address.put("state", "MH");
        address.put("postal_code", "400001");
        address.put("country", "IN");

        Map<String, Object> shipping = new HashMap<>();
        shipping.put("name", "John Doe");
        shipping.put("address", address);

        params.put("shipping", shipping);

        PaymentIntent paymentIntent = PaymentIntent.create(params);


        Map<String, Object> response = new HashMap<>();
        response.put("clientSecret", paymentIntent.getClientSecret());

        Payment payment = new Payment();
        payment.setBookingId(bookingId);
        payment.setUser(authenticatedUser);
        payment.setTotalAmount(amount);
        payment.setPaymentType(PaymentType.DEBITCARD);
        payment.setPaymentStatus(PaymentStatus.INPROGRESS);
        payment.setClientSecret(paymentIntent.getClientSecret());
        payment.setPaymentIntentId(paymentIntent.getId());
        paymentRepository.save(payment);

        return response;
    }

    @Override
    public Payment processPayment(double amount, String currency, String paymentMethodId, String bookingId) throws StripeException {
        Stripe.apiKey = stripeSecretKey;

        Optional<Payment> optionalPayment = paymentRepository.findByBookingId(bookingId);

        if (optionalPayment.isPresent()) {
            Payment payment = optionalPayment.get();

            if (payment.getPaymentStatus() == PaymentStatus.CONFIRMED) {
                return payment;
            }

            PaymentIntent paymentIntent = PaymentIntent.retrieve(payment.getPaymentIntentId());

            if ("succeeded".equals(paymentIntent.getStatus())) {
                payment.setPaymentStatus(PaymentStatus.CONFIRMED);
                paymentRepository.save(payment);
                return payment;
            } else {
                payment.setPaymentStatus(PaymentStatus.CANCELLED);
                paymentRepository.save(payment);
                throw new EntityNotFoundException("Payment failed. Status: " + paymentIntent.getStatus());
            }
        }

        throw new EntityNotFoundException("No payment record found for booking ID: " + bookingId);
    }



}