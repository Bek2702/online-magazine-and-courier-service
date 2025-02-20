package com.example.onlinecourierservices.payload.req;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReqCategory {
    @NotBlank
    private String name;

    private String description;

    private Long parentCategoryId;

}
