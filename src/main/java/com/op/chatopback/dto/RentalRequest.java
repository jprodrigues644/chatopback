package com.op.chatopback.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RentalRequest {

    private String name;
    private Integer surface;
    private Double price;
    private String picture;
    private String description;
}
