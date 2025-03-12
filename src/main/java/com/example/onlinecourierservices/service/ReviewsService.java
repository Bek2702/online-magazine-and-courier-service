package com.example.onlinecourierservices.service;

import com.example.onlinecourierservices.entity.Reviews;
import com.example.onlinecourierservices.entity.User;
import com.example.onlinecourierservices.exceptions.RestException;
import com.example.onlinecourierservices.payload.ApiResult;
import com.example.onlinecourierservices.payload.ReviewsDTO;
import com.example.onlinecourierservices.repository.ProductRepository;
import com.example.onlinecourierservices.repository.ReviewsRepository;
import com.example.onlinecourierservices.utils.MessageConstants;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewsService {
    private final ReviewsRepository reviewsRepository;
    private final ProductRepository productRepository;
    public ApiResult<String> create(User user,ReviewsDTO reviewsDTO) {

        Reviews reviews = Reviews.builder()
                .userId(user)
                .productId(productRepository.findById(reviewsDTO.getProductId()).orElseThrow(
                        () -> RestException.restThrow(MessageConstants.PRODUCT_NOT_FOUNDED)))
                .comment(reviewsDTO.getComment())
                .rating(reviewsDTO.getRating())
                .build();
        reviewsRepository.save(reviews);
        return ApiResult.successResponse(MessageConstants.REVIEWS_SUCCESSFULLY_CREATE);
    }

    public ApiResult<String> update(Long id, ReviewsDTO reviewsDTO) {
        Reviews reviews = reviewsRepository.findById(id).orElseThrow(() ->
                RestException.restThrow(MessageConstants.REVIEWS_NOT_FOUNDED));
        reviews.setComment(reviewsDTO.getComment());
        reviews.setRating(reviewsDTO.getRating());
        reviewsRepository.save(reviews);
        return ApiResult.successResponse(MessageConstants.REVIEWS_SUCCESSFULLY_UPDATE);
    }
}
