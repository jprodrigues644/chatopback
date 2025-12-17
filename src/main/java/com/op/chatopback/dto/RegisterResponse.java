package com.op.chatopback.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class RegisterResponse {
    private Integer id;
    private String name;
    private String email;
    private LocalDateTime registrationDate;
    private String confirmationMessage;
}
