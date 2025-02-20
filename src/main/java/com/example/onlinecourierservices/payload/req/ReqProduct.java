package com.example.onlinecourierservices.payload.req;

import com.example.onlinecourierservices.entity.File;
import com.example.onlinecourierservices.utils.MessageConstants;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReqProduct {
    @NotBlank(message = MessageConstants.PRODUCT_NAME_CAN_NOT_EMPTY)
    private String name;
    private String description;
    @NotBlank(message = MessageConstants.PRODUCT_PRICE_CAN_BE_EMPTY)
    private Double price;
    @NotBlank(message = MessageConstants.PRODUCT_QUANTITY_CAN_NOT_BE_EMPTY)
    private Integer quantity;
    @NotBlank(message = MessageConstants.PRODUCT_CATEGORY_ID_CAN__NOT_BE_EMPTY)
    private Long categoryId;


    private String weight;
    private String dimensions;
    private boolean available;
    @NotBlank
    private String brand;
    private Double discount;

}
