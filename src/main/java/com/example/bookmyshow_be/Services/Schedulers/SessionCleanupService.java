package com.example.bookmyshow_be.Services.Schedulers;

import com.example.bookmyshow_be.Models.SeatBlocking;
import com.example.bookmyshow_be.Models.ShowSeatMapping;
import com.example.bookmyshow_be.Repositories.SeatBlockingRepository;
import com.example.bookmyshow_be.Repositories.ShowSeatMappingRepository;
import com.example.bookmyshow_be.Utils.ENUMS.SeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SessionCleanupService {

    @Autowired
    private SeatBlockingRepository seatBlockingRepository;

    @Autowired
    private ShowSeatMappingRepository showSeatMappingRepository;

    @Scheduled(fixedRate = 60000) // Run every minute
    public void cleanupExpiredSessions() {
        LocalDateTime now = LocalDateTime.now();

        List<SeatBlocking> expiredSessions = seatBlockingRepository.findByExpiryTimeBefore(now);

        for (SeatBlocking session : expiredSessions) {
            List<ShowSeatMapping> seats = showSeatMappingRepository.findAllById(session.getSeatIds());

            seats.forEach(seat -> {
                if(seat.getSeatStatus().equals(SeatStatus.BLOCKED)){
                    seat.setSeatStatus(SeatStatus.AVAILABLE);
                }
            });
            showSeatMappingRepository.saveAll(seats);

            seatBlockingRepository.delete(session);
        }
    }
}
