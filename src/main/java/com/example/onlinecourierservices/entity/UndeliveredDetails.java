package com.example.onlinecourierservices.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


// Yetkazib berilmagan mahsulotlar haqida tafsilotlar
@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UndeliveredDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Order orderId;

    @OneToOne
    private Product productId;

    private String reason; //yetkazib berilmagan sababi

    private LocalDateTime attemptDate; //yetkazib berishga urinish sanasi

    private Boolean reschedule; //Qayta yetkazib berish rejalashtirilganmi

    @OneToOne
    private User courierId;

}











