package com.example.bookmyshow_be.Controllers;

import com.example.bookmyshow_be.DTOs.ResponseWrapper;
import com.example.bookmyshow_be.Exceptions.EntityNotFoundException;
import com.example.bookmyshow_be.Models.User;
import com.example.bookmyshow_be.Services.Payment.StripePaymentService;
import com.stripe.exception.StripeException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/stripe-booking")
public class StripePaymentController {

    private StripePaymentService stripePaymentService;

    @Autowired

    public StripePaymentController(StripePaymentService stripePaymentService) {
        this.stripePaymentService = stripePaymentService;
    }

    @PostMapping("/createPaymentIntent")
    public ResponseEntity<?> createPaymentIntent(
                                        @RequestBody Map<String, Object> request,
                                        HttpServletRequest httpRequest){
        List<Long> selectedSeats = ((List<?>) request.get("selectedSeats"))
                .stream()
                .map(seatId -> ((Number) seatId).longValue()) // Ensures conversion to Long
                .collect(Collectors.toList());
            double amount = (double) request.get("amount");
            String currency = (String) request.get("currency");
            String bookingId = (String) request.get("bookingId");
            Object authenticatedUser = httpRequest.getAttribute("authenticatedUser");
            Long userId;
            if (authenticatedUser instanceof User) {
                userId = ((User) authenticatedUser).getUserId();
            } else {
                throw new EntityNotFoundException("Invalid user information");
            }
        Map<String, Object> response = null;
        try {
            response = stripePaymentService.createPaymentIntent((User) authenticatedUser, amount, currency, bookingId, selectedSeats);
        } catch (StripeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", e.getMessage()));
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(
                    new ResponseWrapper("Payment Intent has been registered successfully!",
                            response));
    }
}
