package com.example.onlinecourierservices.service;

import com.example.onlinecourierservices.entity.Category;
import com.example.onlinecourierservices.exceptions.RestException;
import com.example.onlinecourierservices.payload.ApiResult;
import com.example.onlinecourierservices.payload.CategoryDTO;
import com.example.onlinecourierservices.repository.CategoryRepository;
import com.example.onlinecourierservices.utils.MessageConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public ApiResult<String> create(CategoryDTO categoryDTO) {
        if (categoryRepository.existsByName(categoryDTO.getName())) {
            throw RestException.restThrow(MessageConstants.CATEGORY_ALREADY_CREATE);
        }
        if (categoryDTO.getParentCategoryName().length() != 0) {
            Category parrentCategory = categoryRepository.findByName(categoryDTO.getParentCategoryName()).orElseThrow(
                    () ->
                            RestException.restThrow(MessageConstants.CATEGORY_NOT_FOUND)
            );
            Category category = Category.builder()
                    .name(categoryDTO.getName())
                    .description(categoryDTO.getDescription())
                    .parentCategory(parrentCategory)
                    .build();
            categoryRepository.save(category);
            return ApiResult.successResponse(MessageConstants.CATEGORY_SUCCESSFULLY_CREATE);
        }

        Category build = Category.builder()
                .name(categoryDTO.getName())
                .description(categoryDTO.getDescription())
                .build();

        categoryRepository.save(build);

        return ApiResult.successResponse(MessageConstants.CATEGORY_SUCCESSFULLY_CREATE);
    }

    public ApiResult<List<CategoryDTO>> view() {
        List<CategoryDTO> categoryDTOList = new ArrayList<>();
        for (Category category : categoryRepository.findAll()) {
            categoryDTOList.add(parseCategoryDTO(category));

        }
        return ApiResult.successResponse();
    }

    private CategoryDTO parseCategoryDTO(Category category) {
        return CategoryDTO.builder()
                .name(category.getName())
                .description(category.getDescription())
//                .parentCategoryName(category.getParentCategory())
                .build();
    }

}
