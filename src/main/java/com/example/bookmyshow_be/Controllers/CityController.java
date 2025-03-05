package com.example.bookmyshow_be.Controllers;

import com.example.bookmyshow_be.DTOs.AdminDTOs.AdminRegisterRequestDTO;
import com.example.bookmyshow_be.DTOs.AdminDTOs.AdminRegisterResponseDTO;
import com.example.bookmyshow_be.DTOs.CityDTOs.*;
import com.example.bookmyshow_be.DTOs.PaginationRequest;
import com.example.bookmyshow_be.DTOs.ResponseWrapper;
import com.example.bookmyshow_be.Models.City;
import com.example.bookmyshow_be.Services.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cities")
public class CityController {
    private CityService cityService;

    @Autowired
    public CityController(CityService cityService){
        this.cityService = cityService;
    }

    @PostMapping
    public ResponseEntity<?> addCity(@RequestBody AddCityRequestDTO requestDTO){
        AddCityResponseDTO responseDTO = AddCityResponseDTO.convertCityToDTO
                (cityService.addCity(AddCityRequestDTO.convertDTOToCity(requestDTO)));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseWrapper("City created successfully", responseDTO));
    }

    @GetMapping
    public ResponseEntity<?> getAllCities(@ModelAttribute PaginationRequest paginationRequest){
        List<City> cities = cityService.getAllCities(
                paginationRequest.getPage(),
                paginationRequest.getPageSize(),
                paginationRequest.getSortField(),
                paginationRequest.getSortOrder()
        );

        List<CityDTO> citiesDTO = cities.stream()
                .map(CityDTO::convertCityToDTO)
                .collect(Collectors.toList());


        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseWrapper("Cities fetched successfully", citiesDTO));
    }

    @GetMapping("/{cityId}")
    public ResponseEntity<?> getCityById(@PathVariable("cityId") Long cityId){
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseWrapper("City fetched successfully",
                        CityDTO.convertCityToDTO(cityService.getCityByCityId(cityId))));
    }
}
