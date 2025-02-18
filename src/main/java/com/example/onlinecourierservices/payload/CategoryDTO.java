package com.example.onlinecourierservices.payload;

import com.example.onlinecourierservices.entity.Category;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.beans.factory.annotation.Value;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryDTO {


    private Long id;
    @NotBlank()
    private String name;
    private String description;
    private CategoryDTO parentCategory;
}

