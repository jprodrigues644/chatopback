package com.op.chatopback.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class RegisterResponse {
    private Integer id;
    private String name;
    private String email;
    private LocalDateTime registrationDate;
    private String confirmationMessage;
}
