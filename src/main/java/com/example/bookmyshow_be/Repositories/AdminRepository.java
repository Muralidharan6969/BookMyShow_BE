package com.example.bookmyshow_be.Repositories;

import com.example.bookmyshow_be.Models.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    @Override
    Optional<Admin> findById(Long adminId);
    Optional<Admin> findByMobileNumber(Long mobileNumber);
    Admin save(Admin admin);
}
