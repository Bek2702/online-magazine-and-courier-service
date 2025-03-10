package com.example.onlinecourierservices.payload.res;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResDeliveredDetails {

    private String deliveryAddress;

    private String recipientName;

    private String recipientPhone;

    private Long orderId;
    private String status;
    private LocalDateTime estimatedDeliveryDate;
    private String trackingNumber;
    private Long courierId;

}
