package com.op.chatopback.dto;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
public class RentalRequest {

    private String name;
    private Double surface;
    private Double price;
    private String description;
    private MultipartFile picture;
}
