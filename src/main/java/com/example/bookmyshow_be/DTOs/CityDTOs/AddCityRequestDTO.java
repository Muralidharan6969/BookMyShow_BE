package com.example.bookmyshow_be.DTOs.CityDTOs;

import com.example.bookmyshow_be.DTOs.AdminDTOs.AdminRegisterRequestDTO;
import com.example.bookmyshow_be.Models.Admin;
import com.example.bookmyshow_be.Models.City;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddCityRequestDTO {
    private String cityName;

    public static City convertDTOToCity(AddCityRequestDTO addCityRequestDTO){
        City city = new City();
        city.setCityName(addCityRequestDTO.getCityName());
        return city;
    }
}
