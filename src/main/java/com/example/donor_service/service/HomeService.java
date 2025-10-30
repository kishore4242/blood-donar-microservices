package com.example.donor_service.service;

import com.example.donor_service.dto.DonorResponseDto;
import com.example.donor_service.dto.UserDetailsDto;
import com.example.donor_service.model.Donor;
import com.example.donor_service.model.User;
import com.example.donor_service.repo.ContactDetailsRepository;
import com.example.donor_service.repo.DonorRepository;
import com.example.donor_service.repo.LocationRepository;
import com.example.donor_service.repo.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class HomeService {
    private final UsersRepository usersRepository;
    private final DonorRepository donorRepository;
    private final LocationRepository locationRepository;
    private final ContactDetailsRepository contactDetailsRepository;

    public ResponseEntity<?> getUserDetails(Authentication authentication) {
        String mail = authentication.getName();

        User user = usersRepository.findByEmail(mail).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found for the mail "+mail));

        Donor donor = donorRepository.findByUser_UserId(user.getUserId()).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Donor not found for this account"));

        DonorResponseDto responseDto = generateDonorResponse(user, donor);

        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    private DonorResponseDto generateDonorResponse(User user, Donor donor) {
        DonorResponseDto responseDto = new DonorResponseDto(
                user.getName(),
                user.getEmail(),
                donor.getBloodGroup(),
                donor.getGender(),
                donor.getDob(),
                donor.getLastDonationDate(),
                donor.getLocation() != null ? donor.getLocation().getCity()+" "+donor.getLocation().getState() : null
        );
        return responseDto;
    }

    public ResponseEntity<UserDetailsDto> saveUserDetails(UserDetailsDto userDetailsDto) {
        return null;
    }
}
