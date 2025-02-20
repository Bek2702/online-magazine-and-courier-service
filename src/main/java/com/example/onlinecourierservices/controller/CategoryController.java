package com.example.onlinecourierservices.controller;

import com.example.onlinecourierservices.payload.ApiResult;
import com.example.onlinecourierservices.payload.CategoryDTO;
import com.example.onlinecourierservices.payload.req.ReqCategory;
import com.example.onlinecourierservices.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("category")
public class CategoryController {
    private final CategoryService categoryService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<ApiResult<String>> createCategory(@RequestBody ReqCategory reqCategory) {
        return ResponseEntity.ok(categoryService.create(reqCategory));
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResult<List<CategoryDTO>>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @GetMapping("/main-category")
    public ResponseEntity<ApiResult<List<CategoryDTO>>> getMainCategories() {
        return ResponseEntity.ok(categoryService.getMainCategories());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResult<CategoryDTO>> getId(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.getId(id));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResult<String>> delete(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.delete(id));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResult<String>> update(@PathVariable Long id, @RequestBody ReqCategory resCategory) {
        return ResponseEntity.ok(categoryService.update(id, resCategory));
    }


}
