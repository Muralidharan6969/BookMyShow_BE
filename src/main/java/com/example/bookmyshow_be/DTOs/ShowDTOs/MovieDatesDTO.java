package com.example.bookmyshow_be.DTOs.ShowDTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class MovieDatesDTO {
    private LocalDate date;
    private String day;
    private int dayOfMonth;
    private String month;
}
