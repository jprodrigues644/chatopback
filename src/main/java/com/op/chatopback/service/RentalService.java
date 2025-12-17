package com.op.chatopback.service;

import com.op.chatopback.dto.RentalRequest;
import com.op.chatopback.dto.RentalResponse;
import com.op.chatopback.dto.RentalsResponse;
import com.op.chatopback.mapper.RentalMapper;
import com.op.chatopback.model.Rental;
import com.op.chatopback.model.User;
import com.op.chatopback.repository.RentalRepository;
import com.op.chatopback.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RentalService {
    private final RentalRepository rentalRepository;
    private final UserRepository userRepository;
    private final FileStorageService fileStorageService;

 // Read
 public RentalsResponse getAllRentals() {
     List<RentalResponse> list = rentalRepository.findAll().stream()
             .map(RentalMapper::toResponse)
             .toList();

     return new RentalsResponse(list);
 }

    public Optional<Rental> getRentalById(Integer rentalId){
        return rentalRepository.findById(rentalId);
    }

    public  List<RentalResponse> getAllRentalsByUser(Integer ownerId) {

        return rentalRepository.findAllByOwnerId(ownerId)
                .stream()
                .map(RentalMapper::toResponse)
                .toList();
    }

    // Creations
    public RentalResponse createRental(RentalRequest rentalRequest, Integer authenticatedUserId) {

        User owner = userRepository.findById(authenticatedUserId)
                .orElseThrow(() -> new RuntimeException("User with Id: " + authenticatedUserId + " not fond"));

        String imageUrl = fileStorageService.saveRentalImage(rentalRequest.getPicture());
        Rental rental = RentalMapper.toEntity(rentalRequest, owner, imageUrl);

        rentalRepository.save(rental);
        return RentalMapper.toResponse(rental);

    }


    // Update

    public RentalResponse updateRental(RentalRequest request, Integer rentalId, Integer userId) {

        Rental rental = rentalRepository.findById(rentalId)
                .orElseThrow(() -> new RuntimeException("Rental not found with id: " + rentalId));

        if (!rental.getOwner().getId().equals(userId)) {
            throw new RuntimeException("You are not authorized to update this rental");
        }

        // Updating Only the soutable Editing entries
        rental.setName(request.getName());
        rental.setSurface(request.getSurface());
        rental.setPrice(request.getPrice());
        rental.setDescription(request.getDescription());


        if (request.getPicture() != null && !request.getPicture().isEmpty()) {
            String imageUrl = fileStorageService.saveRentalImage(request.getPicture());
            rental.setPicture(imageUrl);
        }

        rentalRepository.save(rental);
        return RentalMapper.toResponse(rental);

    }
     // Delete
    public void deleteRental(Integer rentalId) {
        rentalRepository.deleteById(rentalId);


    }

}
