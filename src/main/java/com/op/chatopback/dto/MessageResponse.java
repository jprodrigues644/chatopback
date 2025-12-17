package com.op.chatopback.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageResponse {
    private  Integer id;
    private Integer userId;
    private Integer rentalId;
    private String message;
    private String confirmationMessage;
    private LocalDateTime createdAt;
}
