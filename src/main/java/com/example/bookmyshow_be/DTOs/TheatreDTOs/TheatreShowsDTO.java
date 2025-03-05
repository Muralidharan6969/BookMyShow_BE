package com.example.bookmyshow_be.DTOs.TheatreDTOs;

import com.example.bookmyshow_be.DTOs.ShowDTOs.ListShowDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class TheatreShowsDTO {
    private String theatreName;
    private String theatreAddress;
    private List<String> features;
    private List<ListShowDTO> shows;
}
