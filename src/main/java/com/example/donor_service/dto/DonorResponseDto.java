package com.example.donor_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class DonorResponseDto {
    private String name;
    private String email;
    private String bloodGroup;
    private String gender;
    private LocalDate dob;
    private LocalDate lastDonationDate;
    private String location;
}
