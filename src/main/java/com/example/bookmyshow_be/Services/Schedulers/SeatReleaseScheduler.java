package com.example.bookmyshow_be.Services.Schedulers;

import com.example.bookmyshow_be.Models.ShowSeatMapping;
import com.example.bookmyshow_be.Repositories.ShowSeatMappingRepository;
import com.example.bookmyshow_be.Utils.ENUMS.SeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SeatReleaseScheduler {

    @Autowired
    private ShowSeatMappingRepository showSeatMappingRepository;

    @Scheduled(fixedRate = 300000) // Run every 5 minutes
    public void releaseBlockedSeats() {
        LocalDateTime timeoutTime = LocalDateTime.now().minusMinutes(10); // 10 minutes ago
        List<ShowSeatMapping> blockedSeats = showSeatMappingRepository
                .findBySeatStatusAndBlockedAtBefore(SeatStatus.BLOCKED, timeoutTime);

        blockedSeats.forEach(seat -> {
            seat.setSeatStatus(SeatStatus.AVAILABLE);
            seat.setBlockedAt(null); // Clear the blocked timestamp
        });

        showSeatMappingRepository.saveAll(blockedSeats);
    }
}
