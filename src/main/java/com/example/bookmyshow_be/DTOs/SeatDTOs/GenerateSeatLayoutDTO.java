package com.example.bookmyshow_be.DTOs.SeatDTOs;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GenerateSeatLayoutDTO {
    private Long noOfRows;
    private Long noOfColumns;
    private Long noOfPremiumRows;
    private String startChar;
}
