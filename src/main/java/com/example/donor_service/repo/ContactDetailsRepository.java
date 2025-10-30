package com.example.donor_service.repo;

import com.example.donor_service.model.ContactDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContactDetailsRepository extends JpaRepository<ContactDetails, Long> {
    Optional<ContactDetails> findByContactId(Long id);
}
