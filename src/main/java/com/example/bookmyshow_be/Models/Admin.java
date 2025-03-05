package com.example.bookmyshow_be.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Admin extends BaseModel{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long adminId;

    @Column(unique = true)
    private String registrationId;

    @Column(unique = true)
    private Long mobileNumber;

    @Column(nullable = false)
    private String password;
}
