package com.example.bookmyshow_be.Services;

import com.example.bookmyshow_be.DTOs.TicketDTOs.BookingHistoryDTO;
import com.example.bookmyshow_be.DTOs.TicketDTOs.TicketDTO;
import com.example.bookmyshow_be.Models.*;
import com.example.bookmyshow_be.Repositories.ShowRepository;
import com.example.bookmyshow_be.Repositories.ShowSeatMappingRepository;
import com.example.bookmyshow_be.Repositories.TicketRepository;
import com.example.bookmyshow_be.Repositories.UserRepository;
import com.example.bookmyshow_be.Services.Payment.PaymentService;
import com.example.bookmyshow_be.Utils.ENUMS.SeatStatus;
import com.example.bookmyshow_be.Utils.ENUMS.TicketStatus;
import com.stripe.exception.StripeException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BookingService {
    private UserRepository userRepository;
    private ShowRepository showRepository;
    private ShowSeatMappingRepository showSeatMappingRepository;
    private PaymentService paymentService;
    private TicketRepository ticketRepository;
    private ShowService showService;

    public BookingService(UserRepository userRepository, ShowRepository showRepository,
                          ShowSeatMappingRepository showSeatMappingRepository,
                          @Qualifier("stripePaymentService") PaymentService paymentService,
                          TicketRepository ticketRepository, ShowService showService){
        this.userRepository = userRepository;
        this.showRepository = showRepository;
        this.showSeatMappingRepository = showSeatMappingRepository;
        this.paymentService = paymentService;
        this.ticketRepository = ticketRepository;
        this.showService = showService;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Map<String, Object> bookTicket(Long showId, Long userId, double amount, String currency,
                                String paymentMethodId, String bookingId, List<Long> selectedSeats) throws StripeException {
//        showService.validateSession(userId);
//        SeatBlocking seatBlocking = showService.getSeatBlockingByUserId(userId);
//        List<Long> selectedSeats = seatBlocking.getSeatIds();
        Payment payment = paymentService.processPayment(amount, currency, paymentMethodId, bookingId);
        if (payment != null){
            Ticket ticket = generateTicket(showId, selectedSeats, amount, currency, userId);
            ticket.setPayment(payment);
            ticketRepository.save(ticket);

            List<ShowSeatMapping> seats = showSeatMappingRepository.findAllByShowSeatMappingIdIn(selectedSeats);
            seats.forEach(seat -> {
                seat.setSeatStatus(SeatStatus.BOOKED);
                seat.setTicket(ticket);
            });
            showSeatMappingRepository.saveAll(seats);
            return TicketDTO.convertTicketToFrontendFormat(ticket);
        }

        showService.releaseSeats(userId, selectedSeats);
        return null;
    }

    private Ticket generateTicket(Long showId, List<Long> selectedSeats, double amount, String currency, Long userId) {
        Ticket ticket = new Ticket();
        ticket.setTicketStatus(TicketStatus.CONFIRMED);
        ticket.setTicketNumber(generateTicketNumber());
        ticket.setTotalAmount(amount);
        ticket.setBookedAt(LocalDateTime.now());

        // Fetch the show and user from the database
        Show show = showRepository.findById(showId)
                .orElseThrow(() -> new RuntimeException("Show not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        ticket.setShow(show);
        ticket.setBookedByUser(user);

        List<ShowSeatMapping> showSeatMappings = selectedSeats.stream()
                .map(seatId -> showSeatMappingRepository.findById(seatId.longValue())  // Convert Integer to Long
                        .orElseThrow(() -> new RuntimeException("Seat not found: " + seatId)))
                .collect(Collectors.toList());

        ticket.setSeats(showSeatMappings);


        return ticket;
    }

    private String generateTicketNumber() {
        return "TICKET-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    @Transactional(readOnly = true)
    public List<BookingHistoryDTO> getBookingHistory(Long userId) {
        List<Ticket> tickets = ticketRepository.findAllByBookedByUser_UserId(userId);

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("EEE, d MMM yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a");
        DateTimeFormatter bookingDateFormatter = DateTimeFormatter.ofPattern("MMM dd yyyy hh:mma");

        return tickets.stream().map(ticket -> {
            Show show = ticket.getShow();
            String theaterDetails = show.getScreen().getTheatre().getTheatreName() + ": " +
                    show.getScreen().getTheatre().getTheatreAddress();

            String seats = ticket.getSeats().stream()
                    .map(seatMapping -> seatMapping.getSeat().getSeatType() + " - " + seatMapping.getSeat().getSeatNumber())
                    .collect(Collectors.joining(", "));

            String paymentMethod = Optional.ofNullable(ticket.getPayment())
                    .map(Payment::getPaymentType)
                    .map(Enum::toString)
                    .orElse("Not Available");


            return new BookingHistoryDTO(
                    ticket.getTicketNumber(),
                    show.getMovie().getMovieName(),
                    show.getShowDate().format(dateFormatter),
                    show.getShowStartTime().format(timeFormatter),
                    theaterDetails,
                    seats,
                    show.getScreen().getScreenName(),
                    ticket.getTotalAmount(),
                    ticket.getBookedAt().format(bookingDateFormatter),
                    paymentMethod,
                    "M-Ticket",
                    show.getMovie().getMovieImageURL()
            );
        }).collect(Collectors.toList());
    }
}
