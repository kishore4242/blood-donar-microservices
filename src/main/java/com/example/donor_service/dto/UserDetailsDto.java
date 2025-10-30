package com.example.donor_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class UserDetailsDto {
    private String userName;
    private LocalDate dob;
    private String phone;
    private String gender;
    private String bloodGroup;
}
