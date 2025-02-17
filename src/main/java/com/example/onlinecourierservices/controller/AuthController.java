package com.example.onlinecourierservices.controller;

import com.example.onlinecourierservices.payload.ApiResult;
import com.example.onlinecourierservices.payload.auth.AuthLogin;
import com.example.onlinecourierservices.payload.auth.AuthRegister;
import com.example.onlinecourierservices.payload.TokenDTO;
import com.example.onlinecourierservices.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ApiResult<String> register(@RequestBody AuthRegister authRegister) {
        return authService.register(authRegister);
    }

    @PostMapping("/login")
    @Operation(summary = "log qilish",description = "gooooo")
    public ApiResult<TokenDTO> login(@RequestBody AuthLogin authLogin) {
        return authService.login(authLogin);
    }


    @PostMapping("/check-code")
    public ApiResult<String> checkCode(@RequestParam Integer code) {
        return authService.checkCode(code);
    }

    @PostMapping("/forgot-password")
    public ApiResult<String> forgotPassword(@Valid @Email @RequestParam String email) {
        return authService.forgotPassword(email);
    }

    @PostMapping
    public ApiResult<String> home() {
        System.out.println("hello");
        return ApiResult.successResponse("str");
    }
}
