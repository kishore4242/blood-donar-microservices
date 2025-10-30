package com.example.donor_service.repo;

import com.example.donor_service.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<User,  Long> {
    boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);

    @Query("SELECT u.userId FROM User as u WHERE u.email = :email")
    Optional<Long> findUserIdByEmail(String email);
}
