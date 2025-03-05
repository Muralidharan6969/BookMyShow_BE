package com.example.bookmyshow_be.Models;

import com.example.bookmyshow_be.Utils.ENUMS.PaymentStatus;
import com.example.bookmyshow_be.Utils.ENUMS.PaymentType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "payments")
public class Payment extends BaseModel{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="paymentId")
    private Long paymentId;

    @Column(name ="refNo", nullable = true)
    private String refNo;

    @Column(name = "bookingId", unique = true)
    private String bookingId;

    @Column(name ="totalAmount", nullable = false)
    private double totalAmount;

    @Enumerated(EnumType.ORDINAL)
    private PaymentType paymentType;

    @Enumerated(EnumType.ORDINAL)
    private PaymentStatus paymentStatus;

    @ManyToOne
    private User user;

    private String clientSecret;
    private String paymentIntentId;
}
