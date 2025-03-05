package com.example.bookmyshow_be.DTOs.ShowDTOs;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ShowDetailsDTO {
    private String movieName;
    private String theatreDetail;
    private String showDetail;
}
