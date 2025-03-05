package com.example.bookmyshow_be.DTOs.ShowDTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingSummaryDTO {
    private List<CategoryPriceDTO> categoryPrices;
    private double convenienceFee;
    private double totalAmount;
}
