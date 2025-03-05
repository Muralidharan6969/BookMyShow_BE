package com.example.bookmyshow_be.Repositories;

import com.example.bookmyshow_be.Models.Show;
import com.example.bookmyshow_be.Models.ShowSeatType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShowSeatTypeRepository extends JpaRepository<ShowSeatType, Long> {
    List<ShowSeatType> findByShow_ShowId(Long showId);
    ShowSeatType save(ShowSeatType showSeatType);
}
