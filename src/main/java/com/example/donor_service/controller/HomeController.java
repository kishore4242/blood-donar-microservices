package com.example.donor_service.controller;

import com.example.donor_service.dto.UserDetailsDto;
import com.example.donor_service.service.impl.HomeServiceImpl;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HomeController {
    private final HomeServiceImpl homeService;

    @GetMapping({"/home","/"})
    public ResponseEntity<UserDetailsDto> homePage(){

    }
}
