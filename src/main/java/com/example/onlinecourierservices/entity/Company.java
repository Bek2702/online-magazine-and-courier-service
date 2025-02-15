package com.example.onlinecourierservices.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String email;

    @Column(unique = true)
    private String website;
    @Column(nullable = false)
    private String registrationNumber;//royxatdan otish raqami
    @Column(nullable = false)
    private String taxNumber; //Soliq identification raqami

    @Column(nullable = false)
    private LocalDateTime foundedDate;// Kompaniya tashkil topgan sana

    private String description;//Kompaniya haqida qisqacha malumot
}
