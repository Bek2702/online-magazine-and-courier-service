package com.example.onlinecourierservices.controller;

import com.example.onlinecourierservices.payload.ApiResult;
import com.example.onlinecourierservices.payload.req.ReqProduct;
import com.example.onlinecourierservices.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;

    @PostMapping(value = "/create",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResult<String>> createProduct(@RequestParam("file") MultipartFile file, @RequestBody ReqProduct reqProduct) {
        return ResponseEntity.ok(productService.createProduct(reqProduct, file));
    }

    @PostMapping("/home")
    public ResponseEntity<ApiResult<String>> home(){
        return ResponseEntity.ok(ApiResult.successResponse("gooooooooooooooo"));
    }
}
