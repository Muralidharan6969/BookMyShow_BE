package com.example.bookmyshow_be.Services;

import com.example.bookmyshow_be.Exceptions.EntityNotFoundException;
import com.example.bookmyshow_be.Models.City;
import com.example.bookmyshow_be.Repositories.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CityService {
    private CityRepository cityRepository;

    @Autowired
    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public City addCity(City city){
        return cityRepository.save(city);
    }

    public List<String> fetchCityNames(){
        List<String> cityNames = new ArrayList<>();
        cityRepository.findAll().forEach(city -> cityNames.add(city.getCityName()));
        return cityNames;
    }

    public City getCityByCityName(String cityName){
        Optional<City> cityOptional = cityRepository.findByCityName(cityName);
        if(cityOptional.isEmpty()){
            throw new EntityNotFoundException("City not found");
        }
        return cityOptional.get();
    }

    public City getCityByCityId(Long cityId){
        Optional<City> cityOptional = cityRepository.findById(cityId);
        if(cityOptional.isEmpty()){
            throw new EntityNotFoundException("City not found");
        }
        return cityOptional.get();
    }

    public List<City> getAllCities(int page, int pageSize, String sortField, String sortOrder){
        Sort.Direction direction =
                sortOrder.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(direction, sortField));
        Page<City> cityPage = cityRepository.findAll(pageable);
        return cityPage.getContent();
    }
}
