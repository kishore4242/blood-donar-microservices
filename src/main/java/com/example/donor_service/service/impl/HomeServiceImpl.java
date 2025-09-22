package com.example.donor_service.service.impl;

import com.example.donor_service.dto.UserDetailsDto;
import com.example.donor_service.service.repo.HomeServiceRepo;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class HomeServiceImpl implements HomeServiceRepo {
    @Override
    public ResponseEntity<UserDetailsDto> getUsers(String username) {

    }
}
