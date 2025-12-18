package com.op.chatopback.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.op.chatopback.model.Rental;

import java.util.List;

public interface RentalRepository extends JpaRepository<Rental, Integer> {
    /**
     * Finds all rentals owned by a specific user.
     *
     * @param ownerId the ID of the owner
     * @return a list of Rentals owned by the specified user
     */
    List<Rental> findAllByOwnerId(Integer ownerId);
}
