package com.example.onlinecourierservices.entity;

import com.example.onlinecourierservices.entity.enums.ReturnStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class ReturnService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Order purchaseServiceId;

    @OneToOne
    private User userId;

    @OneToOne
    private Product productsId;

    private Integer quantity; //qaytarilayotgan mahsulot soni

    private String reason;//qaytarilish sababi

    @Enumerated(EnumType.STRING)
    private ReturnStatus returnStatus;

    private LocalDateTime returnDate;

    private boolean refundProcessed;
}

