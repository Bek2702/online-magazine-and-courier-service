package com.example.onlinecourierservices.service;

import com.example.onlinecourierservices.entity.OrderItems;
import com.example.onlinecourierservices.exceptions.RestException;
import com.example.onlinecourierservices.payload.ApiResult;
import com.example.onlinecourierservices.repository.OrderItemsRepository;
import com.example.onlinecourierservices.utils.MessageConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class OrderItemsService {
    private final OrderItemsRepository orderItemsRepository;
    public ApiResult<String> updateDec(Long id) {
        OrderItems orderItems = orderItemsRepository.findById(id).orElseThrow(() -> RestException.restThrow(MessageConstants.ORDER_ITEMS_NOT_FOUNDED));
        orderItems.setQuantity(orderItems.getQuantity()-1);
        orderItemsRepository.save(orderItems);
        return ApiResult.successResponse(MessageConstants.ORDER_ITEMS_UPDATE);
    }

    public ApiResult<String> updateInc(Long id) {
        OrderItems orderItems = orderItemsRepository.findById(id).orElseThrow(() -> RestException.restThrow(MessageConstants.ORDER_ITEMS_NOT_FOUNDED));
        orderItems.setQuantity(orderItems.getQuantity()+1);
        orderItemsRepository.save(orderItems);
        return ApiResult.successResponse(MessageConstants.ORDER_ITEMS_UPDATE);
    }

    public ApiResult<String> update(Long id, Integer quantity) {
        OrderItems orderItems = orderItemsRepository.findById(id).orElseThrow(() -> RestException.restThrow(MessageConstants.ORDER_ITEMS_NOT_FOUNDED));
        orderItems.setQuantity(quantity);
        return null;
    }
}
