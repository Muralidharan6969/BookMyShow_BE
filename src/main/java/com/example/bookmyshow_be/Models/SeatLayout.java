package com.example.bookmyshow_be.Models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SeatLayout {
    private Long rowNo;
    private Long colNo;
    private String seatNumber;
}
