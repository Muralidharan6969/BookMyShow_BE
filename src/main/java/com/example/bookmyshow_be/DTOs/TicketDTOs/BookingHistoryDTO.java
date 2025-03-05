package com.example.bookmyshow_be.DTOs.TicketDTOs;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookingHistoryDTO {
    private String id;
    private String movieName;
    private String date;
    private String time;
    private String theater;
    private String seats;
    private String screen;
    private double totalAmount;
    private String bookingDate;
    private String paymentMethod;
    private String ticketType;
    private String movieImageURL;
}
