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
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;
    private String description;
    @Column(nullable = false)
    private Double price;
    @Column(nullable = false)
    private Integer quantity; //Ombordagi miqdori
    @ManyToOne
    @JoinColumn(name = "category_id",nullable = false)
    private Category category;
    @Column(nullable = false)
    private String imageUrl;
    private String weight;
    private String dimensions;// O'lchamlari
    private boolean available; //zaxirada bor yoki yoqligi
    @Column(nullable = false)
    private String brand;
    private Double discount; // Chegirma foizi
    @CreationTimestamp
    private LocalDateTime createAt;
    @UpdateTimestamp
    private LocalDateTime updateAt;
}
