package com.example.bookmyshow_be.DTOs.ShowDTOs;

import com.example.bookmyshow_be.Utils.ENUMS.SeatType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeatCategoryDTO {
    private SeatType seatType;
    private List<String> seatNumbers;
}

