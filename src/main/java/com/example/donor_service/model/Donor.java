package com.example.donor_service.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class Donor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long donorId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",nullable = false)
    @JsonIgnoreProperties("donor")
    private User user;

    private String bloodGroup;
    private LocalDate dob;
    private String gender;
    private LocalDate lastDonationDate;

    @ManyToOne
    @JoinColumn(name = "location_id")
    @JsonIgnoreProperties("donor")
    private Location location;

}
