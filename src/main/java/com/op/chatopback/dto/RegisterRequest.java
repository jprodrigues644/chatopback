package com.op.chatopback.dto;


import lombok.Data;

@Data
public class RegisterRequest {
    String name;
    String email;
    String password;
}
