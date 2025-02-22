package com.example.onlinecourierservices.service;

import com.example.onlinecourierservices.entity.Category;
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

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final FileService fileService;


    public ApiResult<String> createProduct(ReqProduct reqProduct) {
        if (productRepository.existsByName(reqProduct.getName())) {
            throw RestException.restThrow(MessageConstants.PRODUCT_NAME_ALREADY_EXISTS);
        }
        Product product = parceProduct(reqProduct);
        productRepository.save(product);
        return ApiResult.successResponse(MessageConstants.PRODUCT_SUCCESSFULLY_CREATED);
    }


    public Product getById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> RestException.restThrow(MessageConstants.PRODUCT_NOT_FOUNDED));

    }

    public ApiResult<ProductDTO> getProductById(Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(
                        () -> RestException.restThrow(MessageConstants.PRODUCT_NOT_FOUNDED));
        if (!productRepository.findAvailableById(id)) {
            throw RestException.restThrow("Product qolmagan");
        }
        return ApiResult.successResponse(parceProductToProductDTO(product));

    }


    public ApiResult<String> update(Long id, ReqProduct reqProduct) {
        Product product = productRepository.findById(id).orElseThrow(() ->
                RestException.restThrow(MessageConstants.PRODUCT_NOT_FOUNDED));
        product.setName(reqProduct.getName());
        product.setDescription(product.getDescription());
        product.setPrice(product.getPrice());
        product.setQuantity(product.getQuantity());
        product.setCategory(categoryRepository.findById(reqProduct.getCategoryId()).orElseThrow(() ->
                RestException.restThrow(MessageConstants.CATEGORY_NOT_FOUND)));
        product.setWeight(reqProduct.getWeight());
        product.setDimensions(reqProduct.getDimensions());
        product.setBrand(reqProduct.getBrand());
        product.setDiscount(reqProduct.getDiscount());
        productRepository.save(product);

        return ApiResult.successResponse(MessageConstants.PRODUCT_SUCCESSFULLY_UPDATE);
    }

    public ApiResult<List<ProductDTO>> getProductList() {
        List<Product> productList = productRepository.findAll();
        List<ProductDTO> productDTOList = new ArrayList<>();
        for (Product product : productList) {
            productDTOList.add(parceProductToProductDTO(product));
        }
        return ApiResult.successResponse(productDTOList);
    }

    public ApiResult<String> delete(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> RestException.restThrow(MessageConstants.PRODUCT_NOT_FOUNDED));
        productRepository.delete(product);
        return ApiResult.successResponse(MessageConstants.PRODUCT_SUCCESSFULLY_DELETE);
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

    private ProductDTO parceProductToProductDTO(Product product) {
        return ProductDTO.builder()
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .categoryName(product.getCategory().getName())
                .images(product.getImages())
                .dimensions(product.getDimensions())
                .brand(product.getBrand())
                .discount(product.getDiscount())
                .build();
    }

}
