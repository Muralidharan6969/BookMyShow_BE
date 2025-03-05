package com.example.bookmyshow_be.DTOs.TicketDTOs;

import com.example.bookmyshow_be.DTOs.PaymentDTOs.PaymentDTO;
import com.example.bookmyshow_be.DTOs.ShowDTOs.ShowDTO;
import com.example.bookmyshow_be.DTOs.ShowSeatMappingDTOs.ShowSeatMappingDTO;
import com.example.bookmyshow_be.DTOs.UserDTOs.UserDTO;
import com.example.bookmyshow_be.Models.ShowSeatMapping;
import com.example.bookmyshow_be.Models.Ticket;
import com.example.bookmyshow_be.Utils.ENUMS.TicketStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@Setter
public class TicketDTO {
    private Long ticketId;
    private String ticketStatus;
    private String ticketNumber;
    private double totalAmount;
    private LocalDateTime bookedAt;
    private List<ShowSeatMappingDTO> seats; // Updated to use ShowSeatMappingDTO
    private ShowDTO show;
    private UserDTO bookedByUser;
    private PaymentDTO payment;

    public static TicketDTO convertTicketToDTO(Ticket ticket) {
        TicketDTO ticketDTO = new TicketDTO();
        ticketDTO.setTicketId(ticket.getTicketId());
        ticketDTO.setTicketStatus(ticket.getTicketStatus().name());
        ticketDTO.setTicketNumber(ticket.getTicketNumber());
        ticketDTO.setTotalAmount(ticket.getTotalAmount());
        ticketDTO.setBookedAt(ticket.getBookedAt());

        // Map ShowSeatMapping to ShowSeatMappingDTO
        List<ShowSeatMappingDTO> seatMappingDTOs = ticket.getSeats().stream()
                .map(ShowSeatMappingDTO::convertShowSeatMappingToDTO)
                .collect(Collectors.toList());
        ticketDTO.setSeats(seatMappingDTOs);

        // Map show
        ticketDTO.setShow(ShowDTO.convertShowToDTO(ticket.getShow()));

        // Map user
        ticketDTO.setBookedByUser(UserDTO.convertUserToDTO(ticket.getBookedByUser()));

        // Map payment
        ticketDTO.setPayment(PaymentDTO.convertPaymentToDTO(ticket.getPayment()));

        return ticketDTO;
    }

    public static Map<String, Object> convertTicketToFrontendFormat(Ticket ticket) {
        Map<String, Object> response = new HashMap<>();

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("EEE, d MMM, yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm a");

        response.put("ticketNumber", ticket.getTicketNumber());
        response.put("movieName", ticket.getShow().getMovie().getMovieName());
        response.put("theatre", ticket.getShow().getScreen().getTheatre().getTheatreName());
        response.put("location", ticket.getShow().getScreen().getTheatre().getTheatreAddress());
        response.put("date", ticket.getShow().getShowDate().format(dateFormatter));
        response.put("time", ticket.getShow().getShowStartTime().format(timeFormatter));

        List<String> seatNumbers = ticket.getSeats().stream()
                .map(seatMapping -> seatMapping.getSeat().getSeatNumber())
                .collect(Collectors.toList());
        response.put("seats", String.join(", ", seatNumbers));

        response.put("totalAmount", "₹" + String.format("%.2f", ticket.getTotalAmount()));

        response.put("paymentId", ticket.getPayment().getPaymentId());
        response.put("bookingId", ticket.getPayment().getBookingId());

        return response;
    }
}
