package com.example.onlinecourierservices.controller;


import com.example.onlinecourierservices.payload.ApiResult;
import com.example.onlinecourierservices.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/company")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    public ApiResult<String> createCompany(){
        return ApiResult.successResponse("");
    }
}
