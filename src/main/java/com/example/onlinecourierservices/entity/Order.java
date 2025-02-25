package com.example.onlinecourierservices.entity;

import com.example.onlinecourierservices.entity.enums.OrderStatus;
import com.example.onlinecourierservices.entity.enums.PaymentMethod;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User userId;

    @Column(nullable = false)
    private BigDecimal totalPrice;

    private OrderStatus status; //buyurtma holati (Yangi, To'langan, Bekor qilingan  ....)
    @CreationTimestamp
    private LocalDateTime createAt;
    @UpdateTimestamp
    private LocalDateTime updateAt;

    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL)
    List<OrderItems> orderItems;

}
