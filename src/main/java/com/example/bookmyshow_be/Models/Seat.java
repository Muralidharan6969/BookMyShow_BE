package com.example.bookmyshow_be.Models;

import com.example.bookmyshow_be.Utils.ENUMS.SeatType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="seats")
public class Seat extends BaseModel{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="seatId")
    private Long seatId;

    @Column(name ="seatNumber", nullable = false)
    private String seatNumber;

    @Column(name ="seatRow", nullable = false)
    private Long seatRow;

    @Column(name ="seatCol", nullable = false)
    private Long seatCol;

    @ManyToOne
    private Screen screen;

    @Enumerated(EnumType.ORDINAL)
    private SeatType seatType;
}
