package com.op.chatopback.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.op.chatopback.model.User;

import java.util.Optional;

public interface UserRepository  extends JpaRepository<User, Integer> {
/**
     * Finds a user by their email.
     *
     * @param email the email of the user to find
     * @return an Optional containing the found User or empty if not found
     */
    Optional<User> findByEmail(String email);
    
}
