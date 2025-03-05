package com.example.bookmyshow_be.Models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class OutletTokenPayload {
    private Long outletId;
    private String outletOwnershipName;
    private String role;
}
