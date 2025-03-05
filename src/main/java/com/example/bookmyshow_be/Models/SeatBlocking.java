package com.example.bookmyshow_be.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "seat_blockings")
public class SeatBlocking extends BaseModel{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "blocked_seats", joinColumns = @JoinColumn(name = "seat_blocking_id"))
    @Column(name = "seat_id")
    private List<Long> seatIds;

    @Column(name = "expiry_time", nullable = false)
    private LocalDateTime expiryTime;
}
