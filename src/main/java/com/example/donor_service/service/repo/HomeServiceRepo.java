package com.example.donor_service.service.repo;

import com.example.donor_service.dto.UserDetailsDto;
import org.springframework.http.ResponseEntity;

public interface HomeServiceRepo {
    ResponseEntity<UserDetailsDto> getUsers(String username);
}
