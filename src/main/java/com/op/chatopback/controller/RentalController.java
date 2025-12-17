package com.op.chatopback.controller;

import com.op.chatopback.dto.ApiMessageResponse;
import com.op.chatopback.dto.RentalRequest;
import com.op.chatopback.dto.RentalResponse;
import com.op.chatopback.dto.RentalsResponse;
import com.op.chatopback.mapper.RentalMapper;
import com.op.chatopback.model.Rental;
import com.op.chatopback.service.RentalService;
import com.op.chatopback.util.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/rentals")
@RequiredArgsConstructor
public class RentalController {
    private final RentalService rentalService;


    @GetMapping()
    public ResponseEntity<RentalsResponse> getAllRentals() {
        return ResponseEntity.ok(rentalService.getAllRentals());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RentalResponse> getRentalById(@PathVariable Integer id) {
        Rental rental = rentalService.getRentalById(id)
                .orElseThrow(() -> new RuntimeException("Rental not found with id: " + id));

        // Convertir Rental en RentalResponse
        return ResponseEntity.ok(RentalMapper.toResponse(rental));


    }


    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<ApiMessageResponse> createRental(
            @ModelAttribute RentalRequest request,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        rentalService.createRental(request, customUserDetails.getId());
        return ResponseEntity.ok(new ApiMessageResponse("Rental created !"));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiMessageResponse> updateRental(
            @PathVariable Integer id,
            @ModelAttribute RentalRequest request,
            @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        rentalService.updateRental(request, id, customUserDetails.getId());
        return ResponseEntity.ok(new ApiMessageResponse("Rental updated !"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRental(@PathVariable Integer id) {
        rentalService.deleteRental(id);
        return ResponseEntity.noContent().build();
    }

}
