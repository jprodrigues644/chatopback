package com.op.chatopback.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisterRequest {
    String name;
    String email;
    String password;
}
