package com.example.onlinecourierservices.repository;

import com.example.onlinecourierservices.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    boolean existsByUsername (String username);
    boolean existsByEmail (String email);

    Optional<User> findByActivationCode(Integer activationCode);

    Optional<User> findByEmail(String email);

    Optional<User> findByEmailAndEnabledTrue(String email);
}
