package com.op.chatopback.mapper;

import com.op.chatopback.dto.RentalRequest;
import com.op.chatopback.dto.RentalResponse;
import com.op.chatopback.model.Rental;

public class RentalMapper {

    // Entity → Response DTO
    public static RentalResponse toResponse(Rental rental, String confirmationMessage) {
        return new RentalResponse(
                rental.getId(),
                rental.getDescription(),
                rental.getSurface().intValue(),
                rental.getPrice(),
                rental.getPictureUrl(),
                rental.getDescription(),
                rental.getOwner().getId(),
                confirmationMessage,
                rental.getCreatedAt()
        );
    }

    // Request DTO → Entity, Owner added in the service
    public static Rental toEntity(RentalRequest request) {
        Rental rental = new Rental();
        rental.setDescription(request.getDescription());
        rental.setSurface(request.getSurface().doubleValue());
        rental.setPrice(request.getPrice());
        rental.setPictureUrl(request.getPicture());
        return rental;
    }
}
