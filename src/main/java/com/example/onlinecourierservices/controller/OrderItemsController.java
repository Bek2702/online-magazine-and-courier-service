package com.example.onlinecourierservices.controller;

import com.example.onlinecourierservices.entity.OrderItems;
import com.example.onlinecourierservices.payload.ApiResult;
import com.example.onlinecourierservices.repository.OrderItemsRepository;
import com.example.onlinecourierservices.service.OrderItemsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order-items")
public class OrderItemsController {
    private final OrderItemsService orderItemsService;

    @PutMapping("/update/dec/{id}")
    public ResponseEntity<ApiResult<String>> updateDec(@PathVariable Long id){
        
        return ResponseEntity.ok(orderItemsService.updateDec(id));
    }

    @PutMapping("/update/inc/{id}")
    public ResponseEntity<ApiResult<String>> updateInc(@PathVariable Long id) {
        return ResponseEntity.ok(orderItemsService.updateInc(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResult<String>> updateNumber(@PathVariable Long id, @RequestParam Integer quantity) {
        return ResponseEntity.ok(orderItemsService.update(id, quantity));
    }
}
