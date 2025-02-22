package com.example.onlinecourierservices.controller;

import com.example.onlinecourierservices.payload.ApiResult;
import com.example.onlinecourierservices.payload.ProductDTO;
import com.example.onlinecourierservices.payload.req.ReqProduct;
import com.example.onlinecourierservices.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;

    @PostMapping(value = "/create")
    public ResponseEntity<ApiResult<String>> createProduct(@RequestBody ReqProduct reqProduct) {
        return ResponseEntity.ok(productService.createProduct(reqProduct));
    }

    @PutMapping(value = "/update/{id}")
    public ResponseEntity<ApiResult<String>> update(@RequestBody ReqProduct reqProduct, @PathVariable Long id) {
        return ResponseEntity.ok(productService.update(id,reqProduct));
    }

    @GetMapping(value = "/get-one/{id}")
    public ResponseEntity<ApiResult<ProductDTO>> getProductById( @PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @GetMapping(value = "/list")
    public ResponseEntity<ApiResult<List<ProductDTO>>> getAllProduct() {
        return ResponseEntity.ok(productService.getProductList());
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<ApiResult<String>> deleteProduct(@PathVariable Long id) {
        return ResponseEntity.ok(productService.delete(id));
    }

    @PostMapping("/home")
    public ResponseEntity<ApiResult<String>> home() {
        return ResponseEntity.ok(ApiResult.successResponse("gooooooooooooooo"));
    }
}
