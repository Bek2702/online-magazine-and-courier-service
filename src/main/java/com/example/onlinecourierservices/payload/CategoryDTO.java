package com.example.onlinecourierservices.payload;

import com.example.onlinecourierservices.entity.Category;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryDTO {

    @NotBlank()
    private String name;
    private String description;
    private String parentCategoryName;
}

