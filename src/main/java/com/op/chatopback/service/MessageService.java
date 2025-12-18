package com.op.chatopback.service;
import com.op.chatopback.dto.MessageRequest;
import com.op.chatopback.model.Message;
import com.op.chatopback.model.Rental;
import com.op.chatopback.model.User;
import com.op.chatopback.repository.MessageRepository;
import com.op.chatopback.repository.RentalRepository;
import com.op.chatopback.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Service for handling message operations.
 * <p>
 * This service provides functionality to send messages related to rentals.
 * </p>
 */
@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final RentalRepository rentalRepository;
    
    /** Sends a message on behalf of an authenticated user.
     *
     * @param request                the message request containing message details
     * @param authenticatedUserId    the ID of the authenticated user sending the message
     */
    public void sendMessage(MessageRequest request, Integer authenticatedUserId) {

        User user = userRepository.findById(authenticatedUserId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Rental rental = rentalRepository.findById(request.getRentalId())
                .orElseThrow(() -> new RuntimeException("Rental not found"));

        Message message = new Message();
        message.setUser(user);
        message.setRental(rental);
        message.setMessage(request.getMessage());

        messageRepository.save(message);


    }
}




