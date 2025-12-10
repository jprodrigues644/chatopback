package com.op.chatopback.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.op.chatopback.model.Rental;

import java.util.List;

public interface RentalRepository extends JpaRepository<Rental, Integer> {
    List<Rental> findAllByOwnerId(Integer ownerId);
}
