package com.example.bookmyshow_be.Repositories;

import com.example.bookmyshow_be.Models.SeatBlocking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface SeatBlockingRepository extends JpaRepository<SeatBlocking, Long> {
    @Override
    SeatBlocking save(SeatBlocking seatBlocking);

    Optional<SeatBlocking> findByUserId(Long userId);

    List<SeatBlocking> findByExpiryTimeBefore(LocalDateTime now);

    @Query("SELECT sb FROM SeatBlocking sb JOIN sb.seatIds seatId " +
            "WHERE sb.userId = :userId AND sb.expiryTime > :currentTime AND seatId IN :seatIds")
    Optional<SeatBlocking> findByUserIdAndSeatIdsAndExpiryTimeAfter(@Param("userId") Long userId,
                                                                    @Param("seatIds") List<Long> seatIds,
                                                                    @Param("currentTime") LocalDateTime currentTime);

}
