package com.example.bookmyshow_be.Repositories;

import com.example.bookmyshow_be.Models.Outlet;
import com.example.bookmyshow_be.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OutletRepository extends JpaRepository<Outlet, Long> {
    @Override
    Optional<Outlet> findById(Long userId);
    Optional<Outlet> findByEmail(String email);
    Optional<Outlet> findByMobileNumber(Long mobileNumber);
    Outlet save(Outlet outlet);
}
