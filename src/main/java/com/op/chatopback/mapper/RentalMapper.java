package com.op.chatopback.mapper;

import com.op.chatopback.dto.RentalRequest;
import com.op.chatopback.dto.RentalResponse;
import com.op.chatopback.model.Rental;
import com.op.chatopback.model.User;

public class RentalMapper {

    // Entity → Response DTO
    public static RentalResponse toResponse(Rental rental) {
        return new RentalResponse(
                rental.getId(),
                rental.getName(),
                rental.getSurface(),
                rental.getPrice(),
                rental.getPicture(),
                rental.getDescription(),
                rental.getOwner().getId(),
                rental.getCreatedAt(),
                rental.getUpdatedAt()
        );
    }

    // Request DTO → Entity
    public static Rental toEntity(RentalRequest request, User owner, String imageUrl) {
        Rental rental = new Rental();
        rental.setName(request.getName());
        rental.setSurface(request.getSurface());
        rental.setPrice(request.getPrice());
        rental.setDescription(request.getDescription());
        rental.setPicture(imageUrl);
        rental.setOwner(owner);
        return rental;
    }
}
