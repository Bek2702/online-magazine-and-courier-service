package com.example.onlinecourierservices.controller;

import com.example.onlinecourierservices.entity.User;
import com.example.onlinecourierservices.payload.ApiResult;
import com.example.onlinecourierservices.payload.OrderDTO;
import com.example.onlinecourierservices.security.CurrenUser;
import com.example.onlinecourierservices.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order")
public class OrderController {

    private final OrderService orderService;
    @PostMapping("/create")
    public ResponseEntity<ApiResult<String>> createOrder(@CurrenUser User currentUser,@RequestParam(value = "productId") Long productId) {
        return ResponseEntity.ok(orderService.createOrder(currentUser,productId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResult<OrderDTO>> getById(@PathVariable Long id){
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResult<String>> deleteOrder(@PathVariable Long id){
        return ResponseEntity.ok(orderService.deleteOrder(id));
    }
}
