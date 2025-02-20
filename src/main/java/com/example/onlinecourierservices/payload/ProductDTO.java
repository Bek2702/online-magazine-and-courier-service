package com.example.onlinecourierservices.payload;

import com.example.onlinecourierservices.utils.MessageConstants;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDTO {

    @NotBlank(message = MessageConstants.PRODUCT_NAME_CAN_NOT_EMPTY)
    private String name;
}

