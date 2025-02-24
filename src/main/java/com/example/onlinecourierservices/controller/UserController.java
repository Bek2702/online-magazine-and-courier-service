package com.example.onlinecourierservices.controller;

import com.example.onlinecourierservices.entity.User;
import com.example.onlinecourierservices.payload.ApiResult;
import com.example.onlinecourierservices.security.CurrenUser;
import com.example.onlinecourierservices.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/profile")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

//    @PostMapping()
//    public ApiResult<String> logOut(){
//        return ApiResult.successResponse("log out");
//    }

    @PostMapping
    public ApiResult<User> getUser(@CurrenUser User user){
        return ApiResult.successResponse(user);
    }
}
