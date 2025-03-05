package com.example.bookmyshow_be.Repositories;

import com.example.bookmyshow_be.Models.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    @Override
    Ticket save(Ticket ticket);

    List<Ticket> findAllByBookedByUser_UserId(Long userId);
}
