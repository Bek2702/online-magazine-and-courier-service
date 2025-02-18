package com.example.onlinecourierservices.payload.res;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResCategory {
    @NotBlank
    private String name;

    private String description;

    private Long parentCategoryId;

}
