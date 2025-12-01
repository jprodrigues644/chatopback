package com.op.chatopback.dto;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class RentalResponse {
    private  Integer id;
    private String name;
    private Integer surface;
    private Double price;
    private String picture;
    private String description;
    private Integer ownerId ;

    private LocalDateTime createdAt;

}
