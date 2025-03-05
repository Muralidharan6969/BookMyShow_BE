package com.example.bookmyshow_be.Repositories;

import com.example.bookmyshow_be.Models.ShowSeatMapping;
import com.example.bookmyshow_be.Utils.ENUMS.SeatStatus;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Lock;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ShowSeatMappingRepository extends JpaRepository<ShowSeatMapping, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<ShowSeatMapping> findById(@Param("id") Long id);
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT ssm FROM ShowSeatMapping ssm WHERE ssm.showSeatMappingId IN :ids")
    List<ShowSeatMapping> findAllByShowSeatMappingIdIn(@Param("ids") List<Long> ids);
    List<ShowSeatMapping> findByShow_ShowId(Long showId);
    @Override
    ShowSeatMapping save(ShowSeatMapping showSeatMapping);

    List<ShowSeatMapping> findBySeatStatusAndBlockedAtBefore(SeatStatus seatStatus, LocalDateTime blockedAt);

    @Query("SELECT COUNT(ssm) FROM ShowSeatMapping ssm WHERE ssm.show.showId = :showId AND ssm.seatStatus != :status")
    long countBookedSeatsForShow(@Param("showId") Long showId, @Param("status") SeatStatus status);

}
