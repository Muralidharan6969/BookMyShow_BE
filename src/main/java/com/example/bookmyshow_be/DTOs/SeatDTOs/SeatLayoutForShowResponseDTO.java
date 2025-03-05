package com.example.bookmyshow_be.DTOs.SeatDTOs;

import com.example.bookmyshow_be.Utils.ENUMS.SeatStatus;
import com.example.bookmyshow_be.Utils.ENUMS.SeatType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SeatLayoutForShowResponseDTO {
    private Long showSeatMappingId;
    private String seatNumber;
    private Long seatRow;
    private Long seatCol;
    private SeatType seatType;
    private SeatStatus seatStatus;
    private double price;
}
