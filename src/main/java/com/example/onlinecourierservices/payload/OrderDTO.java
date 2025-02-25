package com.example.onlinecourierservices.payload;

import com.example.onlinecourierservices.entity.OrderItems;
import com.example.onlinecourierservices.entity.enums.OrderStatus;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDTO {
    private BigDecimal totalPrice;
    private String status;
    private List<OrderItemsDTO> orderItems;
}
