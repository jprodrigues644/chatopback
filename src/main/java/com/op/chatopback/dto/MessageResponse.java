package com.op.chatopback.dto;

import lombok.Data;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
public class MessageResponse {
    private  Integer id;
    private Integer userId;
    private Integer rentalId;
    private String message;
    private String confirmationMessage;
    private LocalDateTime createdAt;
}
