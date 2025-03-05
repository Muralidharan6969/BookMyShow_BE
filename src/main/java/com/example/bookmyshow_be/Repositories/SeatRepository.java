package com.example.bookmyshow_be.Repositories;

import com.example.bookmyshow_be.Models.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SeatRepository extends JpaRepository<Seat, Long> {
    @Override
    Optional<Seat> findById(Long aLong);

    @Override
    Seat save(Seat seat);
}
