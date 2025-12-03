package com.op.chatopback.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MessageRequest {
    private Integer userId;
    private Integer rentalId;
    private String message;
}
