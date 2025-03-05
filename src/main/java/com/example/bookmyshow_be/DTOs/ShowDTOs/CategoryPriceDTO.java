package com.example.bookmyshow_be.DTOs.ShowDTOs;

import com.example.bookmyshow_be.Utils.ENUMS.SeatType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryPriceDTO {
    private SeatType seatType;
    private int ticketCount;
    private double ticketPricePerSeat;
    private double totalPrice;
}
