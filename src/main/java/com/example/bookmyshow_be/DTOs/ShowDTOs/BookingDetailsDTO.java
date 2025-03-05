package com.example.bookmyshow_be.DTOs.ShowDTOs;

import com.example.bookmyshow_be.DTOs.TheatreDTOs.TheatreDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingDetailsDTO {
    private String bookingId;
    private MovieDetailsDTO movieDetails;
    private TheatresDTO theatre;
    private ShowDetailssDTO showDetails;
    private BookingSummaryDTO bookingSummary;
}

