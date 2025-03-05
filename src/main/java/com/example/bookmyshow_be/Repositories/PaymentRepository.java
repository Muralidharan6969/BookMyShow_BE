package com.example.bookmyshow_be.Repositories;

import com.example.bookmyshow_be.Models.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Optional<Payment> findByBookingId(String bookingId);

    @Override
    Payment save(Payment payment);
}
