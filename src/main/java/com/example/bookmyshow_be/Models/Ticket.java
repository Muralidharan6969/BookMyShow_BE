package com.example.bookmyshow_be.Models;

import com.example.bookmyshow_be.Utils.ENUMS.TicketStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "tickets")
public class Ticket extends BaseModel{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="ticketId")
    private Long ticketId;

    @Enumerated(EnumType.ORDINAL)
    private TicketStatus ticketStatus;

    @Column(name ="ticketNumber", nullable = false)
    private String ticketNumber;

    @Column(name ="totalAmount", nullable = false)
    private double totalAmount;

    @Column(name ="bookedAt", nullable = false)
    private LocalDateTime bookedAt;

    @OneToMany (mappedBy = "ticket", fetch = FetchType.EAGER)
    private List<ShowSeatMapping> seats = new ArrayList<>();

    @ManyToOne
    private Show show;

    @ManyToOne
    private User bookedByUser;

    @OneToOne
    private Payment payment;
}
