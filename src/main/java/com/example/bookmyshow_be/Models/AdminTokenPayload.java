package com.example.bookmyshow_be.Models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AdminTokenPayload {
    private Long adminId;
    private String registrationId;
    private String role;
}
