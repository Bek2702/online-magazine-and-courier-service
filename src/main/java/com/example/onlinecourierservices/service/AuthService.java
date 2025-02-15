package com.example.onlinecourierservices.service;

import com.example.onlinecourierservices.entity.User;
import com.example.onlinecourierservices.entity.enums.UserRole;
import com.example.onlinecourierservices.exceptions.RestException;
import com.example.onlinecourierservices.payload.*;
import com.example.onlinecourierservices.repository.UserRepository;
import com.example.onlinecourierservices.security.JwtTokenProvider;
import com.example.onlinecourierservices.utils.MessageConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    @Autowired
    private final PasswordEncoder passwordEncoder;
    private final EmailSenderService emailSender;
    private final JwtTokenProvider jwtTokenProvider;

    public ApiResult<String> register(AuthRegister auth) {

        if (!Objects.equals(auth.getPassword(), auth.getPrePassword()))
            throw RestException.restThrow(MessageConstants.PASSWORDS_AND_PRE_PASSWORD_NOT_EQUAL);
        if (userRepository.existsByUsername(auth.getUserName()))
            throw RestException.restThrow(MessageConstants.USER_ALREADY_REGISTERED);
        if (userRepository.existsByEmail(auth.getEmail()))
            throw RestException.restThrow(MessageConstants.EMAIL_ALREADY_REGISTERED);

        User user = User.builder()
                .username(auth.getUserName())
                .email(auth.getEmail())
                .phoneNumber(auth.getPhoneNumber())
                .activationCode(generateFiveDigitNumber())
                .password(passwordEncoder.encode(auth.getPassword()))
                .userRole(UserRole.ROLE_USER)
                .build();

        emailSender.sendEmail(auth.getEmail(), "Your activation code: ", user.getActivationCode().toString());

        userRepository.save(user);

        return ApiResult.successResponse("Successfully, Code sent to your email. Please activate your profile");
    }


    public ApiResult<String> checkCode(Integer code) {
        Optional<User> user = userRepository.findByActivationCode(code);
        if (user.isEmpty()) {
            return ApiResult.successResponse("Activation code did not match");
        }
        User user1 = user.get();
        user1.setActivationCode(null);
        user1.setEnabled(true);
        userRepository.save(user1);
        return ApiResult.successResponse("Successfully");

    }


    public ApiResult<TokenDTO> login(AuthLogin authLogin) {
        User user = userRepository.findByEmail(authLogin.getEmail())
                .orElseThrow(() ->
                        RestException.
                                restThrow(MessageConstants.USER_NOT_FOUND));
        if (!passwordEncoder.matches(authLogin.getPassword(), user.getPassword())) {
            throw RestException.restThrow(MessageConstants.PASSWORD_ERROR);
        }
        if (!user.isEnabled()) {
            throw RestException.restThrow(MessageConstants.USER_NOT_FOUND_OR_DISABLED);
        }
        String token = jwtTokenProvider.generateToken(user.getEmail());
        return ApiResult.successResponse(TokenDTO.builder()
                .accessToken(token)
                .build());
    }

    public ApiResult<String> forgotPassword(String email) {
        User user = userRepository.findByEmailAndEnabledTrue(email).orElseThrow(()
                -> RestException.restThrow(MessageConstants.USER_NOT_FOUND));

        return null;
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    private Integer generateFiveDigitNumber() {
        return new Random().nextInt(90000) + 10000;
    }
}
