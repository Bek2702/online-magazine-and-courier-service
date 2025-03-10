package com.example.onlinecourierservices.service;

import com.example.onlinecourierservices.entity.Order;
import com.example.onlinecourierservices.entity.OrderItems;
import com.example.onlinecourierservices.entity.Product;
import com.example.onlinecourierservices.entity.User;
import com.example.onlinecourierservices.entity.enums.OrderStatus;
import com.example.onlinecourierservices.exceptions.RestException;
import com.example.onlinecourierservices.payload.ApiResult;
import com.example.onlinecourierservices.payload.OrderDTO;
import com.example.onlinecourierservices.payload.OrderItemsDTO;
import com.example.onlinecourierservices.repository.OrderItemsRepository;
import com.example.onlinecourierservices.repository.OrderRepository;
import com.example.onlinecourierservices.repository.ProductRepository;
import com.example.onlinecourierservices.utils.MessageConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
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
//                orderItemsRepository.save(orderItems);
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
        orderItems.setOrder(order);

        orderRepository.save(order);
//        orderItemsRepository.save(orderItems);
        return ApiResult.successResponse(MessageConstants.ORDER_SUCCESSFULLY_CREATED);
    }

    public ApiResult<OrderDTO> getOrderById(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> RestException.restThrow(MessageConstants.ORDER_NOT_FOUNDED));


        OrderDTO orderDTO = OrderDTO.builder()
                .orderItems(order.getOrderItems().stream().map(this::parceOrderItemsDTO).toList())
                .status(order.getStatus().name())
                .totalPrice(BigDecimal.ZERO)
                .build();

        BigDecimal totalPrice = BigDecimal.ZERO;
        for (OrderItemsDTO orderItem : orderDTO.getOrderItems()) {
            totalPrice = totalPrice.add(orderItem.getPrice());
        }
        orderDTO.setTotalPrice(totalPrice);

//        orderDTO.setTotalPrice(order.getTotalPrice());
        return ApiResult.successResponse(orderDTO);
    }

    private OrderItemsDTO parceOrderItemsDTO(OrderItems orderItem) {

        return OrderItemsDTO.builder()
                .orderID(orderItem.getOrder().getId())
                .productId(orderItem.getProduct().getId())
                .quantity(orderItem.getQuantity())
                .price(orderItem.getPrice().multiply(new BigDecimal(orderItem.getQuantity())))
                .build();
    }

    public ApiResult<String> deleteOrder(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> RestException.restThrow(MessageConstants.ORDER_NOT_FOUNDED));
        orderRepository.delete(order);
        return ApiResult.successResponse("Order deleted");
    }

    public ApiResult<String> orderCanceled(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> RestException.restThrow(MessageConstants.ORDER_NOT_FOUNDED));
        order.setStatus(OrderStatus.CANCELED);
        orderRepository.save(order);
        return ApiResult.successResponse("Order canceled");
    }
}
