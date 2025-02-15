package com.example.onlinecourierservices.component;

import com.example.onlinecourierservices.entity.User;
import com.example.onlinecourierservices.entity.enums.UserRole;
import com.example.onlinecourierservices.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() == 0) {
            userRepository.save(User.builder()
                    .username("bek")
                    .email("asilbek0416@gmail.com")
                    .phoneNumber("+998912254007")
                    .enabled(true)
                    .password(passwordEncoder.encode("root123"))
                    .userRole(UserRole.ROLE_ADMIN)
                    .build());

        }
    }
}
