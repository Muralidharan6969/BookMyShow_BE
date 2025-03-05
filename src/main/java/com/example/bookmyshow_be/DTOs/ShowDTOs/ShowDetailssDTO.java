package com.example.bookmyshow_be.DTOs.ShowDTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShowDetailssDTO {
    private String date;
    private String time;
    private List<SeatCategoryDTO> seatCategories;
}
