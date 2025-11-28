package com.op.chatopback.model;
import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "rentals")
@Data 
public class Rental {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
   
    private Double surface;
    private Double price;
    @Column(length = 255)
    private String pictureUrl;
    @Column(length = 2000)
    private String description;
    @Column(name = "owner_id", nullable = false)
    private Integer ownerId;

    private Timestamp createdAt;
    private Timestamp updatedAt;



 

}
