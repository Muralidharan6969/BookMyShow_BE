package com.example.bookmyshow_be.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Outlet extends BaseModel{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long outletId;

    @Column(nullable = false)
    private String outletOwnershipName;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private Long mobileNumber;

    @Column(nullable = false)
    private String password;
}
