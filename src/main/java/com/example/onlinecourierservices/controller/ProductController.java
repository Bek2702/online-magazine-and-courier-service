package com.example.onlinecourierservices.controller;

import com.example.onlinecourierservices.payload.ApiResult;
import com.example.onlinecourierservices.payload.ProductDTO;
import com.example.onlinecourierservices.payload.req.ReqProduct;
import com.example.onlinecourierservices.payload.res.ResPageable;
import com.example.onlinecourierservices.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/product")
public class ProductController {
    private final ProductService productService;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping(value = "/create")
    public ResponseEntity<ApiResult<String>> createProduct(@RequestBody ReqProduct reqProduct) {
        return ResponseEntity.ok(productService.createProduct(reqProduct));
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PutMapping(value = "/update/{id}")
    public ResponseEntity<ApiResult<String>> update(@RequestBody ReqProduct reqProduct, @PathVariable Long id) {
        return ResponseEntity.ok(productService.update(id, reqProduct));
    }

    @GetMapping(value = "/get-one/{id}")
    public ResponseEntity<ApiResult<ProductDTO>> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @GetMapping(value = "/list")
    public ResponseEntity<ApiResult<List<ProductDTO>>> getAllProduct() {
        return ResponseEntity.ok(productService.getProductList());
    }
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<ApiResult<String>> deleteProduct(@PathVariable Long id) {
        return ResponseEntity.ok(productService.delete(id));
    }

    @PostMapping("/search")
    public ResponseEntity<ApiResult<ResPageable>> searchProduct(
            @RequestParam(required = false,value = "name") String name,
            @RequestParam(required = false,value = "categoryId") Long categoryId,
            @RequestParam(required = false,value = "startPrice")Double startPrice,
            @RequestParam(required = false,value = "endPrice")Double endPrice,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(productService.search(name,categoryId,startPrice,endPrice,page,size));
    }
}
