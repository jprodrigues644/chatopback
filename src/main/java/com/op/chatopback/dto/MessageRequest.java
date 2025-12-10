package com.op.chatopback.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MessageRequest {

   @JsonProperty("user_id")
    private Integer userId;

    @JsonProperty("rental_id")
    private Integer rentalId;

    private String message;
}
