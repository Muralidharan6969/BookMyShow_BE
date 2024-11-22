package com.example.bookmyshow_be.Repositories;

import com.example.bookmyshow_be.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
