package com.example.bookmyshow_be.Repositories;

import com.example.bookmyshow_be.Models.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {
    @Override
    List<City> findAll();

    @Override
    Optional<City> findById(Long aLong);

    Optional<City> findByCityName(String cityName);

    @Override
    City save(City city);
}
