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

/**
 * Controller for handling rental-related endpoints.
 * <p>
 * Provides endpoints for creating, retrieving, updating, and deleting rentals.
 * </p>
 */

@RestController
@RequestMapping("/api/rentals")
@RequiredArgsConstructor
public class RentalController {
    private final RentalService rentalService;
    /**
     * Retrieves all rentals.
     *
     * @return a response entity containing the list of rentals
     */
    @GetMapping()
    public ResponseEntity<RentalsResponse> getAllRentals() {
        return ResponseEntity.ok(rentalService.getAllRentals());
    }

    /**
     * Retrieves a rental by its ID.
     *
     * @param id the ID of the rental to retrieve
     * @return a response entity containing the rental details
     */
    @GetMapping("/{id}")
    public ResponseEntity<RentalResponse> getRentalById(@PathVariable Integer id) {
        Rental rental = rentalService.getRentalById(id)
                .orElseThrow(() -> new RuntimeException("Rental not found with id: " + id));

        return ResponseEntity.ok(RentalMapper.toResponse(rental));


    }

    /**
     * Creates a new rental.
     *
     * @param request            the rental request containing rental details
     * @param customUserDetails  the authenticated user's details
     * @return a response entity indicating success
     */
    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<ApiMessageResponse> createRental(
            @ModelAttribute RentalRequest request,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        rentalService.createRental(request, customUserDetails.getId());
        return ResponseEntity.ok(new ApiMessageResponse("Rental created !"));
    }

    /**
     * Updates an existing rental.
     *
     * @param id                 the ID of the rental to update
     * @param request            the rental request containing updated rental details
     * @param customUserDetails  the authenticated user's details
     * @return a response entity indicating success
     */
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiMessageResponse> updateRental(
            @PathVariable Integer id,
            @ModelAttribute RentalRequest request,
            @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        rentalService.updateRental(request, id, customUserDetails.getId());
        return ResponseEntity.ok(new ApiMessageResponse("Rental updated !"));
    }
    /**
     * Deletes a rental by its ID.
     *
     * @param id the ID of the rental to delete
     * @return a response entity indicating success
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRental(@PathVariable Integer id) {
        rentalService.deleteRental(id);
        return ResponseEntity.noContent().build();
    }

}
