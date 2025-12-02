package com.op.chatopback.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RegisterResponse {
    private Integer id;
    private String name;
    private String email;
    private LocalDateTime registrationDate;
    private String confirmationMessage;
}
