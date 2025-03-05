package com.example.bookmyshow_be.Models;

import com.example.bookmyshow_be.Utils.ENUMS.SeatType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "showSeatTypes")
public class ShowSeatType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="showSeatTypeId")
    private Long showSeatTypeId;

    @Column(name ="price", nullable = false)
    private double price;

    @Enumerated(EnumType.ORDINAL)
    private SeatType seatType;

    @ManyToOne
    private Show show;
}
