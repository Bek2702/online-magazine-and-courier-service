package com.example.onlinecourierservices.payload;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReviewsDTO {
    @NotNull(message = "Product id bush bolishi mimkin emas")
    private Long ProductId;
    @Min(value = 1,message = "Rating kamida 1 bo'lishi kerak")
    @Max(value = 5,message = "Rating ko'pi bila 5 bo'lishi kerak")
    private int rating;
    private String comment;
}
