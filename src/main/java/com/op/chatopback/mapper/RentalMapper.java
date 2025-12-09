package com.op.chatopback.mapper;

import com.op.chatopback.dto.RentalRequest;
import com.op.chatopback.dto.RentalResponse;
import com.op.chatopback.model.Rental;
import com.op.chatopback.model.User;

public class RentalMapper {

    // Entity → Response DTO
    public static RentalResponse toResponse(Rental rental, String confirmationMessage) {
        return new RentalResponse(
                rental.getId(),
                rental.getSurface(),
                rental.getPrice(),
                rental.getPictureUrl(),
                rental.getDescription(),
                rental.getOwner().getId(),
                confirmationMessage,
                rental.getCreatedAt()
        );
    }

    // Request DTO → Entity,
    public static Rental toEntity(RentalRequest request, User Owner) {
        Rental rental = new Rental();
        rental.setSurface(request.getSurface());
        rental.setPrice(request.getPrice());
        rental.setDescription(request.getDescription());
        rental.setPictureUrl(request.getPicture());
        rental.setOwner(owner);
        return rental;
    }
}
