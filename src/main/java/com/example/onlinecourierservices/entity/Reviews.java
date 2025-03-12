package com.example.onlinecourierservices.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Reviews {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private User userId;

    @OneToOne
    private Product productId;

    private int rating;

    @Column(length = 500)
    private String comment;

    @CreationTimestamp
    private LocalDateTime createDate;
}
