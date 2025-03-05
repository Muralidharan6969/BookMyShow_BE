package com.example.bookmyshow_be.DTOs.SeatDTOs;

import com.example.bookmyshow_be.Utils.ENUMS.SeatType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SeatLayoutResponseDTO {
    private String seatNumber;
    private SeatType seatType;
}
