package com.example.bookmyshow_be.DTOs.ShowDTOs;

import com.example.bookmyshow_be.Utils.ENUMS.SeatType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShowSeatTypeDTO {
    private SeatType seatType;
    private double price;
}
