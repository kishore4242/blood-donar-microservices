package com.example.donor_service.controller;

import com.example.donor_service.dto.ApiResponse;
import com.example.donor_service.dto.LoginDto;
import com.example.donor_service.dto.RegisterDto;
import com.example.donor_service.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class LoginController {
    private final LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<?>> loginUser(@RequestBody LoginDto loginDto){
        return loginService.loginUserService(loginDto.getEmail(),loginDto.getPassword());
    }

    @PostMapping("/signup")
    public ResponseEntity<String> registerUser(@RequestBody RegisterDto registerDto){
        return loginService.registerUser(registerDto);
    }
}
