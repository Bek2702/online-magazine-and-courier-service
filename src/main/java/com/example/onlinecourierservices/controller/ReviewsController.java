package com.example.onlinecourierservices.controller;

import com.example.onlinecourierservices.entity.User;
import com.example.onlinecourierservices.payload.ApiResult;
import com.example.onlinecourierservices.payload.ReviewsDTO;
import com.example.onlinecourierservices.security.CurrenUser;
import com.example.onlinecourierservices.service.ReviewsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewsController {
    private final ReviewsService reviewsService;

    @PostMapping("/create")
    public ResponseEntity<ApiResult<String>> create(@CurrenUser User user, @Valid @RequestBody ReviewsDTO reviewsDTO) {
        return ResponseEntity.ok(reviewsService.create(user, reviewsDTO));
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<ApiResult<String>> update(@PathVariable Long id, @Valid @RequestBody ReviewsDTO reviewsDTO) {
        return ResponseEntity.ok(reviewsService.update(id, reviewsDTO));
    }
}
