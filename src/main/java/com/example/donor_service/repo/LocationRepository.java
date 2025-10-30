package com.example.donor_service.repo;

import com.example.donor_service.model.Donor;
import com.example.donor_service.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
    Optional<Location> findByLocationId(Long id);
}
