package com.example.bookmyshow_be.Controllers;

import com.example.bookmyshow_be.DTOs.ResponseWrapper;
import com.example.bookmyshow_be.DTOs.TicketDTOs.BookingHistoryDTO;
import com.example.bookmyshow_be.DTOs.TicketDTOs.TicketDTO;
import com.example.bookmyshow_be.Exceptions.EntityNotFoundException;
import com.example.bookmyshow_be.Models.Ticket;
import com.example.bookmyshow_be.Models.User;
import com.example.bookmyshow_be.Services.BookingService;
import com.stripe.exception.StripeException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/bookings")
public class BookingController {
    private BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/shows/{showId}/book-ticket")
    public ResponseEntity<?> bookTicket(@PathVariable Long showId,
                                        @RequestBody Map<String, Object> request,
                                        HttpServletRequest httpRequest) {
        List<Long> selectedSeats = ((List<?>) request.get("selectedSeats"))
                .stream()
                .map(seatId -> ((Number) seatId).longValue()) // Ensures conversion to Long
                .collect(Collectors.toList());
        double amount = (double) request.get("amount");
        String currency = (String) request.get("currency");
        String paymentMethodId = (String) request.get("paymentMethodId");
        String bookingId = (String) request.get("bookingId");
        Object authenticatedUser = httpRequest.getAttribute("authenticatedUser");
        Long userId;

        if (authenticatedUser instanceof User) {
            userId = ((User) authenticatedUser).getUserId();
        } else {
            throw new EntityNotFoundException("Invalid user information");
        }

        try {
            var bookingResult = bookingService.bookTicket(showId, userId, amount, currency, paymentMethodId, bookingId, selectedSeats);

            if (bookingResult == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Map.of("error", "Booking failed. Please try again."));
            }

            return ResponseEntity.status(HttpStatus.CREATED).body(
                    new ResponseWrapper("Ticket has been booked successfully!", bookingResult));

        } catch (StripeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/booking-history/{userId}")
    public ResponseEntity<?> getBookingHistory(@PathVariable Long userId) {
        List<BookingHistoryDTO> bookingHistory = bookingService.getBookingHistory(userId);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseWrapper("Ticket has been booked successfully!", bookingHistory));
    }

}
