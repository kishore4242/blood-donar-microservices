package com.example.donor_service.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "\"user\"")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String name;
    private String email;
    private String password;

}
