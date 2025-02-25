package com.example.onlinecourierservices.service;

import com.example.onlinecourierservices.entity.Order;
import com.example.onlinecourierservices.entity.OrderItems;
import com.example.onlinecourierservices.entity.Product;
import com.example.onlinecourierservices.entity.User;
import com.example.onlinecourierservices.entity.enums.OrderStatus;
import com.example.onlinecourierservices.exceptions.RestException;
import com.example.onlinecourierservices.payload.ApiResult;
import com.example.onlinecourierservices.payload.OrderDTO;
import com.example.onlinecourierservices.repository.OrderItemsRepository;
import com.example.onlinecourierservices.repository.OrderRepository;
import com.example.onlinecourierservices.repository.ProductRepository;
import com.example.onlinecourierservices.utils.MessageConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final OrderItemsRepository orderItemsRepository;

    public ApiResult<String> createOrder(User currentUser, Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> RestException.restThrow(MessageConstants.PRODUCT_NOT_FOUNDED));
        OrderItems orderItems = OrderItems.builder()
                .product(product)
                .price(product.getPrice())
                .quantity(1)
                .build();

        Optional<Order> byUserId = orderRepository.findByUserId(currentUser);
        if (byUserId.isPresent()) {
            Order order = byUserId.get();
            if (order.getStatus().equals(OrderStatus.PENDING)) {
                List<OrderItems> orderItemsList = order.getOrderItems();
                orderItemsList.add(orderItems);
                order.setTotalPrice(order.getTotalPrice().add(orderItems.getPrice()));
                orderRepository.save(order);
                return ApiResult.successResponse(MessageConstants.ORDER_SUCCESSFULLY_CREATED);
            }
        }
        List<OrderItems> orderItemsList = new ArrayList<>();
        orderItemsList.add(orderItems);
        Order order = Order.builder()
                .orderItems(orderItemsList)
                .status(OrderStatus.PENDING)
                .totalPrice(orderItems.getPrice())
                .userId(currentUser)
                .build();

        orderRepository.save(order);

        return ApiResult.successResponse(MessageConstants.ORDER_SUCCESSFULLY_CREATED);
    }

    public ApiResult<OrderDTO> getOrderById(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> RestException.restThrow(MessageConstants.ORDER_NOT_FOUNDED));
        BigDecimal totalPrice = new BigDecimal(0);
        for (OrderItems orderItem : order.getOrderItems()) {
            totalPrice.add(orderItem.getPrice().multiply(new BigDecimal(orderItem.getQuantity())));
        }

        return ApiResult.successResponse(OrderDTO.builder()
                .orderItems(order.getOrderItems())
                .totalPrice(totalPrice)
                .status(order.getStatus())
                .build());
    }

    public ApiResult<String> deleteOrder(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> RestException.restThrow(MessageConstants.ORDER_NOT_FOUNDED));
        orderRepository.delete(order);
        return ApiResult.successResponse("Order deleted");
    }
}
