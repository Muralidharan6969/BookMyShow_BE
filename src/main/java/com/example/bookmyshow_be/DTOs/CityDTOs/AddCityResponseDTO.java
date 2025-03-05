package com.example.bookmyshow_be.DTOs.CityDTOs;

import com.example.bookmyshow_be.DTOs.AdminDTOs.AdminRegisterResponseDTO;
import com.example.bookmyshow_be.Models.City;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddCityResponseDTO {
    private Long cityId;
    private String cityName;

    public static AddCityResponseDTO convertCityToDTO(City city){
        AddCityResponseDTO responseDTO = new AddCityResponseDTO();
        responseDTO.setCityId(city.getCityId());
        responseDTO.setCityName(city.getCityName());
        return responseDTO;
    }
}
