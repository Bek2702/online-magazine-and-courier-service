package com.example.onlinecourierservices.controller;

import com.example.onlinecourierservices.entity.Category;
import com.example.onlinecourierservices.payload.ApiResult;
import com.example.onlinecourierservices.payload.CategoryDTO;
import com.example.onlinecourierservices.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("category")
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping("/create-category")
    public ResponseEntity<ApiResult<String>> createCategory(@RequestBody CategoryDTO categoryDTO){
        return ResponseEntity.ok(categoryService.create(categoryDTO));
    }
    @GetMapping("/view")
    public ResponseEntity<ApiResult<List<CategoryDTO>>> viewsParentCategory(){
        return ResponseEntity.ok(categoryService.view());
    }
}
