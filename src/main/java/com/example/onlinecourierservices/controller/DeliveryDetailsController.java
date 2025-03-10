package com.example.onlinecourierservices.controller;

import com.example.onlinecourierservices.payload.ApiResult;
import com.example.onlinecourierservices.payload.DeliveryDetailsDTO;
import com.example.onlinecourierservices.payload.res.ResDeliveredDetails;
import com.example.onlinecourierservices.service.DeliveryDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/delivery")
public class DeliveryDetailsController {
    private final DeliveryDetailsService deliveryDetailsService;

    @PostMapping()
    public ResponseEntity<ApiResult<String>> create(@RequestBody DeliveryDetailsDTO deliveryDetailsDTO) {
        return ResponseEntity.ok(deliveryDetailsService.create(deliveryDetailsDTO));
    }

    @PutMapping("/delivered/{id}")
    public ResponseEntity<ApiResult<String>> delivered(@PathVariable Long id) {
        return ResponseEntity.ok(deliveryDetailsService.delivered(id));
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResult<String>> update(@PathVariable Long id,@RequestBody DeliveryDetailsDTO deliveryDetailsDTO) {
        return ResponseEntity.ok(deliveryDetailsService.update(id,deliveryDetailsDTO));
    }

    @PutMapping("/canceled/{id}")
    public ResponseEntity<ApiResult<String>> canceled(@PathVariable Long id) {
        return ResponseEntity.ok(deliveryDetailsService.canceled(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResult<ResDeliveredDetails>> getId(@PathVariable Long id) {
        return ResponseEntity.ok(deliveryDetailsService.getId(id));
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResult<List<ResDeliveredDetails>>> getAllDeliverDetails() {
        return ResponseEntity.ok(deliveryDetailsService.getAllDeliveredDetails());
    }
}
