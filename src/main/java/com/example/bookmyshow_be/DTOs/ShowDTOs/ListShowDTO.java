package com.example.bookmyshow_be.DTOs.ShowDTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ListShowDTO {
    private Long showId;
    private String showStartTime;
    private String availabilityStatus;
    private String screenName;
}
