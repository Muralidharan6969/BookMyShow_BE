package com.example.bookmyshow_be.Repositories;

import com.example.bookmyshow_be.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Override
    Optional<User> findById(Long outletId);
    Optional<User> findByEmail(String email);
    Optional<User> findByMobileNumber(Long mobileNumber);
    User save(User user);
}