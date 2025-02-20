package com.example.onlinecourierservices.payload;

import com.example.onlinecourierservices.entity.Category;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.beans.factory.annotation.Value;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryDTO {

    @Schema(hidden = true)
    private Long id;
    @NotBlank()
    private String name;
    private String description;
    private CategoryDTO parentCategory;
}

