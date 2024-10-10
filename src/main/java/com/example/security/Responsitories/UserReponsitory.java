package com.example.security.Responsitories;

import com.example.security.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserReponsitory extends JpaRepository<User, Integer> {
    @Query("select u from User u where u.us = ?1")
    Optional<User> findByUsername(String username);
}
