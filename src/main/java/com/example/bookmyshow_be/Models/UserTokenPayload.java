package com.example.bookmyshow_be.Models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserTokenPayload {
    private Long userId;
    private String name;
    private String role;
}
