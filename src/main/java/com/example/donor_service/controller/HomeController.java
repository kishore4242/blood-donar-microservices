package com.example.donor_service.controller;

import com.example.donor_service.service.HomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HomeController {

    private final HomeService homeService;

    @GetMapping("/home")
    public ResponseEntity<?> homePage(Authentication authentication){
        return homeService.getUserDetails(authentication);
    }

//    @PostMapping("/")

}
