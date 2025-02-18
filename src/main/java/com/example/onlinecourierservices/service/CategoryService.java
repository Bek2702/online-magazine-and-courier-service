package com.example.onlinecourierservices.service;

import com.example.onlinecourierservices.entity.Category;
import com.example.onlinecourierservices.exceptions.RestException;
import com.example.onlinecourierservices.payload.ApiResult;
import com.example.onlinecourierservices.payload.CategoryDTO;
import com.example.onlinecourierservices.payload.res.ResCategory;
import com.example.onlinecourierservices.repository.CategoryRepository;
import com.example.onlinecourierservices.utils.MessageConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public ApiResult<String> create(CategoryDTO categoryDTO) {
        if (categoryRepository.existsByName(categoryDTO.getName())) {
            throw RestException.restThrow(MessageConstants.CATEGORY_ALREADY_CREATE);
        }
        if (categoryDTO.getParentCategory() == null) {
            Category parrentCategory = categoryRepository.findByName(categoryDTO.getParentCategory().getName()).orElseThrow(
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

    public ApiResult<List<CategoryDTO>> getMainCategories() {
        List<CategoryDTO> categoryDTOList = new ArrayList<>();
        for (Category category : categoryRepository.findAllByParentCategoryIsNull()) {
            categoryDTOList.add(parseCategoryDTO(category));

        }
        return ApiResult.successResponse(categoryDTOList);
    }

    public ApiResult<List<CategoryDTO>> getAllCategories() {
        List<CategoryDTO> categoryDTOList = new ArrayList<>();
        for (Category category : categoryRepository.findAll()) {

            categoryDTOList.add(parseCategoryDTO(category));
        }
        return ApiResult.successResponse(categoryDTOList);
    }

    private CategoryDTO parseCategoryDTO(Category category) {
//        List<CategoryDTO> categoryDTOList = new ArrayList<>();
//        for (Category category1 : categoryRepository.findByParentCategory(category)) {
//            categoryDTOList.add(CategoryDTO.builder()
//                    .name(category1.getName())
//                    .description(category1.getDescription())
//                    .id(category1.getId())
//                    .build());
//        }
        return CategoryDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .parentCategory((category.getParentCategory() == null) ? null : parseCategoryDTO(categoryRepository.findById(category.getParentCategory().getId()).orElseThrow(
                        () -> RestException.restThrow(MessageConstants.CATEGORY_NOT_FOUND)
                )))
//                .parentCategory(
//                        categoryDTOList
//                )
                .build();
    }


    public ApiResult<CategoryDTO> getId(Long id) {
        return ApiResult.successResponse(
                parseCategoryDTO(categoryRepository
                        .findById(id)
                        .orElseThrow(() ->
                                RestException.restThrow(MessageConstants.CATEGORY_NOT_FOUND))));
    }

    public ApiResult<String> delete(Long id) {
        categoryRepository.delete(categoryRepository.findById(id).orElseThrow(() ->
                RestException.restThrow(MessageConstants.CATEGORY_NOT_FOUND)));
        return ApiResult.successResponse("Category deleted");
    }

    public ApiResult<String> update(Long id, ResCategory resCategory) {
        Category category = categoryRepository.findById(id).orElseThrow(() ->
                RestException.restThrow(MessageConstants.CATEGORY_NOT_FOUND));
        Category parentCategory = categoryRepository.findById(resCategory.getParentCategoryId()).orElse(null);

        category.setName(resCategory.getName());
        category.setDescription(resCategory.getDescription());
        category.setParentCategory(parentCategory);

        categoryRepository.save(category);
        return ApiResult.successResponse(MessageConstants.CATEGORY_SUCCESSFULLY_UPDATE);
    }
}
