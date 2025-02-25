package com.example.onlinecourierservices.payload;

import com.example.onlinecourierservices.entity.File;
import com.example.onlinecourierservices.utils.MessageConstants;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDTO {

    private String name;
    private String description;
    private BigDecimal price;
    private Integer quantity;
    private String categoryName;
    private List<File> images;
    private String dimensions;
    private String weight;
    private boolean available;
    private String brand;
    private Double discount;
}

