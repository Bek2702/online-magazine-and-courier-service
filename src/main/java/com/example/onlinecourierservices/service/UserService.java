package com.example.onlinecourierservices.service;

import com.example.onlinecourierservices.entity.User;
import com.example.onlinecourierservices.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;


    public User getUser() {
        return null;
    }
}
