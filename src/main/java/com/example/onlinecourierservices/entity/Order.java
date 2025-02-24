package com.example.onlinecourierservices.entity;

import com.example.onlinecourierservices.entity.enums.OrderStatus;
import com.example.onlinecourierservices.entity.enums.PaymentMethod;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

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

    @ManyToOne
    private Product productId;

    @Builder.Default
    private Integer quantity = 1; // sotib olinayotgan mahsulot miqdori

    @Column(nullable = false)
    @Builder.Default
    private Double totalPrice = 0.0;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;//(Karta, Naqd, Paypal ........)


    private OrderStatus status; //buyurtma holati (Yangi, To'langan, Bekor qilingan  ....)

    @CreationTimestamp
    private LocalDateTime purchaseDate; //sotib olingan sana va vaqt


}
