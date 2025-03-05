package com.example.bookmyshow_be.Models;

import com.example.bookmyshow_be.Utils.ENUMS.SeatStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "showSeatMappings")
public class ShowSeatMapping extends BaseModel{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="showSeatMappingId")
    private Long showSeatMappingId;

    @ManyToOne
    private Show show;

    @ManyToOne
    private Seat seat;

    @ManyToOne
    private Ticket ticket;

    @Enumerated(EnumType.ORDINAL)
    private SeatStatus seatStatus;

    @Column(name = "blocked_at")
    private LocalDateTime blockedAt;
}
