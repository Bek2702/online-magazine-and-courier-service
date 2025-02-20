package com.example.onlinecourierservices.service;

import com.example.onlinecourierservices.entity.Category;
import com.example.onlinecourierservices.entity.File;
import com.example.onlinecourierservices.entity.Product;
import com.example.onlinecourierservices.exceptions.RestException;
import com.example.onlinecourierservices.payload.ApiResult;
import com.example.onlinecourierservices.payload.ProductDTO;
import com.example.onlinecourierservices.payload.req.ReqProduct;
import com.example.onlinecourierservices.repository.CategoryRepository;
import com.example.onlinecourierservices.repository.ProductRepository;
import com.example.onlinecourierservices.utils.MessageConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final FileService fileService;


    public ApiResult<String> createProduct(ReqProduct reqProduct, MultipartFile file) {
        if (productRepository.existsByName(reqProduct.getName())) {
            throw RestException.restThrow(MessageConstants.PRODUCT_NAME_ALREADY_EXISTS);
        }
        if (file.isEmpty()) {
            throw RestException.restThrow(MessageConstants.PRODUCT_IMAGES_CAN_NOT_BE_EMPTY);
        }
        List<File> fileList = new ArrayList<>();
        fileList.add(fileService.saveFiles(file));

        Product product = parceProduct(reqProduct);
        product.setImages(fileList);
        productRepository.save(product);
        return ApiResult.successResponse(MessageConstants.PRODUCT_SUCCESSFULLY_CREATED);
    }

    private Product parceProduct(ReqProduct reqProduct) {
        Category category = categoryRepository.findById(reqProduct.getCategoryId()).orElseThrow(() -> RestException.restThrow(MessageConstants.CATEGORY_NOT_FOUND));
        return Product.builder()
                .name(reqProduct.getName())
                .description(reqProduct.getDescription())
                .price(reqProduct.getPrice())
                .quantity(reqProduct.getQuantity())
                .category(category)
                .weight(reqProduct.getWeight())
                .dimensions(reqProduct.getDimensions())
                .available(reqProduct.isAvailable())
                .brand(reqProduct.getBrand())
                .discount(reqProduct.getDiscount())
                .build();
    }
}
