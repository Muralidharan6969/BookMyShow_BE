package com.example.bookmyshow_be.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Entity
@Table(name = "shows")
public class Show extends BaseModel{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="showId")
    private Long showId;

    @Column(name ="showDate", nullable = false)
    private LocalDate showDate;

    @Column(name ="showStartTime", nullable = false)
    private LocalTime showStartTime;

    @ManyToOne
    private Movie movie;

    @ManyToOne
    private Screen screen;
}
