package com.example.bookmyshow_be.DTOs.CityDTOs;

import com.example.bookmyshow_be.Models.City;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CityDTO {
    private Long cityId;
    private String cityName;

    public static CityDTO convertCityToDTO(City city){
        CityDTO cityDTO = new CityDTO();
        cityDTO.setCityId(city.getCityId());
        cityDTO.setCityName(city.getCityName());
        return cityDTO;
    }
}
