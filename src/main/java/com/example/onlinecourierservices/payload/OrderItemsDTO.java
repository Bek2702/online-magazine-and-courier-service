package com.example.onlinecourierservices.payload;

import com.example.onlinecourierservices.entity.Order;
import com.example.onlinecourierservices.entity.Product;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.math.BigDecimal;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItemsDTO {
    private Long orderID;
    private Long productId;
    private Integer quantity;
    private BigDecimal price;
}
