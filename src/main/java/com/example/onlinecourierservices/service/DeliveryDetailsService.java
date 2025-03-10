package com.example.onlinecourierservices.service;

import com.example.onlinecourierservices.entity.DeliveryDetails;
import com.example.onlinecourierservices.entity.Order;
import com.example.onlinecourierservices.entity.enums.DeliveryStatus;
import com.example.onlinecourierservices.exceptions.RestException;
import com.example.onlinecourierservices.payload.ApiResult;
import com.example.onlinecourierservices.payload.DeliveryDetailsDTO;
import com.example.onlinecourierservices.payload.res.ResDeliveredDetails;
import com.example.onlinecourierservices.repository.DeliveryDetailsRepository;
import com.example.onlinecourierservices.repository.OrderRepository;
import com.example.onlinecourierservices.utils.MessageConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DeliveryDetailsService {
    private final DeliveryDetailsRepository deliveryDetailsRepository;
    private final OrderRepository orderRepository;

    public ApiResult<String> create(DeliveryDetailsDTO deliveryDetailsDTO) {

        Order order = orderRepository.findById(deliveryDetailsDTO.getOrderId()).orElseThrow(() -> RestException.restThrow(MessageConstants.ORDER_NOT_FOUNDED));

        DeliveryDetails deliveryDetails = DeliveryDetails.builder()
                .deliveryAddress(deliveryDetailsDTO.getDeliveryAddress())
                .recipientName(deliveryDetailsDTO.getRecipientName())
                .orderId(order)
                .recipientPhone(deliveryDetailsDTO.getRecipientPhone())
                .status(DeliveryStatus.ON_THE_WAY)
                .estimatedDeliveryDate(LocalDateTime.now().plusDays(2))
                .build();

        deliveryDetailsRepository.save(deliveryDetails);
        return ApiResult.successResponse(MessageConstants.DELIVERY_DETAILS_SUCCESSFULLY_CREATE);
    }

    public ApiResult<String> delivered(Long id) {
        DeliveryDetails deliveryDetails = deliveryDetailsRepository.findById(id)
                .orElseThrow(() -> RestException.restThrow(MessageConstants.DELIVERY_DETAILS_NOT_FOUNDED));
        deliveryDetails.setStatus(DeliveryStatus.DELIVERED);
        deliveryDetailsRepository.save(deliveryDetails);

        return ApiResult.successResponse(MessageConstants.DELIVERY_DETAILS_SUCCESSFULLY_DELIVERED);
    }

    public ApiResult<String> update(Long id, DeliveryDetailsDTO deliveryDetailsDTO) {
        DeliveryDetails deliveryDetails = deliveryDetailsRepository.findById(id).orElseThrow(() -> RestException.restThrow(MessageConstants.DELIVERY_DETAILS_NOT_FOUNDED));
        deliveryDetails.setDeliveryAddress(deliveryDetails.getDeliveryAddress());
        deliveryDetails.setRecipientName(deliveryDetailsDTO.getRecipientName());
        deliveryDetails.setRecipientPhone(deliveryDetailsDTO.getRecipientPhone());

        deliveryDetailsRepository.save(deliveryDetails);
        return ApiResult.successResponse(MessageConstants.DELIVERY_DETAILS_SUCCESSFULLY_UPDATE);
    }

    public ApiResult<String> canceled(Long id) {
        DeliveryDetails deliveryDetails = deliveryDetailsRepository.findById(id).orElseThrow(() -> RestException.restThrow(MessageConstants.DELIVERY_DETAILS_NOT_FOUNDED));
        deliveryDetails.setStatus(DeliveryStatus.CANCELED);
        deliveryDetailsRepository.save(deliveryDetails);
        return ApiResult.successResponse("Delivery details successfully canceled");
    }

    public ApiResult<ResDeliveredDetails> getId(Long id) {
        DeliveryDetails deliveryDetails = deliveryDetailsRepository.findById(id).orElseThrow(() -> RestException.restThrow(MessageConstants.DELIVERY_DETAILS_NOT_FOUNDED));
        return ApiResult.successResponse(parseDeliveredDetails(deliveryDetails));

    }

    private static ResDeliveredDetails parseDeliveredDetails(DeliveryDetails deliveryDetails) {
        return ResDeliveredDetails.builder()
                .courierId(deliveryDetails.getCourierId().getId())
                .status(deliveryDetails.getStatus().name())
                .orderId(deliveryDetails.getOrderId().getId())
                .deliveryAddress(deliveryDetails.getDeliveryAddress())
                .recipientName(deliveryDetails.getRecipientName())
                .recipientPhone(deliveryDetails.getRecipientPhone())
                .estimatedDeliveryDate(deliveryDetails.getEstimatedDeliveryDate())
                .trackingNumber(deliveryDetails.getTrackingNumber())
                .build();
    }

    public ApiResult<List<ResDeliveredDetails>> getAllDeliveredDetails() {
        List<ResDeliveredDetails> resDeliveredDetails = new ArrayList<>();
        for (DeliveryDetails deliveryDetails : deliveryDetailsRepository.findAll()) {
            resDeliveredDetails.add(parseDeliveredDetails(deliveryDetails));
        }

        return ApiResult.successResponse(resDeliveredDetails);
    }
}
