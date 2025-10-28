package com.example.donor_service.service;

import com.example.donor_service.dto.ApiResponse;
import com.example.donor_service.dto.RegisterDto;
import com.example.donor_service.model.User;
import com.example.donor_service.repo.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final AuthenticationManager authenticationManager;
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;

    /**
     *
     * login service authenticate user and return JWT token as a output
     * @Param username the user's email address
     * @Param password the user's password
     */
    public ResponseEntity<ApiResponse<?>> loginUserService(String username, String password) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password));

            if(authentication.isAuthenticated()) {
                String token = jwtService.generateToken(username);

                HashMap<String, String> dataMap = new HashMap<>();
                dataMap.put("username", username);
                dataMap.put("token", token);
                dataMap.put("roles", authentication.getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.joining(",")));

                return ResponseEntity.ok(new ApiResponse<>(true, dataMap, "Login successful and JWT generated"));
            }

            return ResponseEntity.ok(new ApiResponse<>(false, null, "Invalid credentials"));
        } catch (BadCredentialsException ex) {
            return ResponseEntity.ok(new ApiResponse<>(false, null, "Invalid username or password!"));
        } catch (UsernameNotFoundException ex) {
            return ResponseEntity.ok(new ApiResponse<>(false, null, "User not found!"));
        } catch (Exception ex) {
            return ResponseEntity.ok(new ApiResponse<>(false, null, "Login failed: " + ex.getMessage()));
        }
    }

    /**
     *
     * To save user details into the database
     * @Param registerDto the DTO containing register user details
     */
    public ResponseEntity<String> registerUser(RegisterDto registerDto) {
        if(!isUserAlreadyAvailable(registerDto.getEmail())) {
            User user = new User();
            user.setName(registerDto.getName());
            user.setEmail(registerDto.getEmail());
            user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
            usersRepository.save(user);
            return new ResponseEntity<>("Registered Register. Login to Continue", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("User already available in this email",HttpStatus.CONFLICT);
    }

    /**
     *
     * check user is already present or not
     * @Param email the user's email address
     */
    private boolean isUserAlreadyAvailable(String email){
        return usersRepository.existsByEmail(email);
    }
}
