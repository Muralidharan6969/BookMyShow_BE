package com.example.bookmyshow_be.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name="screens")
@NoArgsConstructor
@AllArgsConstructor
public class Screen extends BaseModel{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="screenId")
    private Long screenId;

    @Column(name ="screenName", nullable = false)
    private String screenName;

    @Column(name ="totalSeats", nullable = false)
    private Long totalSeats;

    @Column(name ="noOfRows", nullable = false)
    private Long noOfRows;

    @Column(name ="noOfColumns", nullable = false)
    private Long noOfColumns;

    @Column(name ="noOfPremiumSeats", nullable = false)
    private Long noOfPremiumRows;

    @ManyToOne
    private Theatre theatre;

    @OneToMany (mappedBy = "screen", fetch = FetchType.LAZY)
    private List<Seat> seatList = new ArrayList<>();

    @OneToMany(mappedBy = "screen", fetch = FetchType.LAZY)
    private List<Show> shows;
}
