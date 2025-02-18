package com.example.onlinecourierservices.controller;

import com.example.onlinecourierservices.payload.ApiResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    @PostMapping
    public ResponseEntity<ApiResult<String>> createProduct(@RequestBody ){

    }
}
