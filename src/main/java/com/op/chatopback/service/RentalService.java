package com.op.chatopback.service;

import com.op.chatopback.dto.RegisterRequest;
import com.op.chatopback.dto.RentalRequest;
import com.op.chatopback.dto.RentalResponse;
import com.op.chatopback.mapper.RentalMapper;
import com.op.chatopback.model.Rental;
import com.op.chatopback.model.User;
import com.op.chatopback.repository.RentalRepository;
import com.op.chatopback.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RentalService {
    private final RentalRepository rentalRepository;
    private final UserRepository userRepository;

    public RentalResponse CreateRental(RentalRequest rentalRequest, Integer ownerId) {

        User owner = userRepository.findById(ownerId).orElseThrow(() -> new RuntimeException("User with Id: " + ownerId + " not fond"));
        Rental rental = RentalMapper.toEntity(rentalRequest, owner);
        rentalRepository.save(rental);
        return RentalMapper.toResponse(rental, "Rental Created");

    }

    //
    // RentalResponse

    public RentalResponse UpdateRental(RentalRequest request, Integer rentalId) {

        Rental rental = rentalRepository.findById(rentalId)
                .orElseThrow(() -> new RuntimeException("Rental not found with id: " + rentalId));

        // Updating Only the soutable Editing entries

        rental.setSurface(request.getSurface());
        rental.setPrice(request.getPrice());
        rental.setDescription(request.getDescription());
        rental.setPictureUrl(request.getPicture());

        rentalRepository.save(rental);

        return RentalMapper.toResponse(rental, "Rental Updated");

    }


    public Void DeleteRental()

}
