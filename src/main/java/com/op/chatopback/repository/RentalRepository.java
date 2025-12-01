package com.op.chatopback.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.op.chatopback.model.Rental;

public interface RentalRepository extends JpaRepository<Rental, Integer> {

}
