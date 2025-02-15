package com.example.onlinecourierservices.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class CentralRates {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String rateType;  // Stavka turi(masalan, Delever Fee, Service charge

    @Column(nullable = false)
    @Builder.Default
    private Double rateValue = 0.0; // Stavka qimati(% yoki pul birligi)

    @Column(nullable = false)
    private String currency; // Valyuta (USD,UZS...)

    @UpdateTimestamp
    private LocalDateTime localDateTime; // Oxirgi yangilanish sanasi


}
