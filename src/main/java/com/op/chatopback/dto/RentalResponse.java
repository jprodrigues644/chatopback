package com.op.chatopback.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class RentalResponse {
    private Integer id;
    private String name;
    private Double surface;
    private Double price;
    private String picture;
    private String description;

    @JsonProperty("owner_id")
    private Integer ownerId;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;
}