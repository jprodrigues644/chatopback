package com.op.chatopback.mapper;

import com.op.chatopback.dto.MessageRequest;
import com.op.chatopback.dto.MessageResponse;
import com.op.chatopback.model.Message;
import com.op.chatopback.model.Rental;
import com.op.chatopback.model.User;

public class MessageMapper {

    // Request DTO → Entity
    public static Message toEntity(MessageRequest request, User user, Rental rental) {
        Message message = new Message();
        message.setMessage(request.getMessage());
        message.setUser(user);
        message.setRental(rental);
        return message;
    }

    // Entity → Response DTO
    public static MessageResponse toResponse(Message entity, String confirmationMessage) {
        return new MessageResponse(
                entity.getId(),
                entity.getUser().getId(),
                entity.getRental().getId(),
                entity.getMessage(),
                confirmationMessage,
                entity.getCreatedAt()
        );
    }
}
