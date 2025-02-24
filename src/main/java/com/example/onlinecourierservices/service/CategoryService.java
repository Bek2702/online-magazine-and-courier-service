package com.example.onlinecourierservices.service;

import com.example.onlinecourierservices.entity.Category;
import com.example.onlinecourierservices.exceptions.RestException;
import com.example.onlinecourierservices.payload.ApiResult;
import com.example.onlinecourierservices.payload.CategoryDTO;
import com.example.onlinecourierservices.payload.req.ReqCategory;
import com.example.onlinecourierservices.payload.res.ResPageable;
import com.example.onlinecourierservices.repository.CategoryRepository;
import com.example.onlinecourierservices.utils.MessageConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public ApiResult<String> create(ReqCategory reqCategory) {
        if (categoryRepository.existsByName(reqCategory.getName())) {
            throw RestException.restThrow(MessageConstants.CATEGORY_ALREADY_CREATE);
        }
        if (reqCategory.getParentCategoryId() != 0) {
            Category parrentCategory = categoryRepository.findById(reqCategory.getParentCategoryId()).orElseThrow(
                    () ->
                            RestException.restThrow(MessageConstants.CATEGORY_NOT_FOUND)
            );
            Category category = Category.builder()
                    .name(reqCategory.getName())
                    .description(reqCategory.getDescription())
                    .parentCategory(parrentCategory)
                    .build();
            categoryRepository.save(category);
            return ApiResult.successResponse(MessageConstants.CATEGORY_SUCCESSFULLY_CREATE);
        }

        Category build = Category.builder()
                .name(reqCategory.getName())
                .description(reqCategory.getDescription())
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

    public ApiResult<String> update(Long id, ReqCategory resCategory) {
        Category category = categoryRepository.findById(id).orElseThrow(() ->
                RestException.restThrow(MessageConstants.CATEGORY_NOT_FOUND));
        Category parentCategory = categoryRepository.findById(resCategory.getParentCategoryId()).orElse(null);

        category.setName(resCategory.getName());
        category.setDescription(resCategory.getDescription());
        category.setParentCategory(parentCategory);

        categoryRepository.save(category);
        return ApiResult.successResponse(MessageConstants.CATEGORY_SUCCESSFULLY_UPDATE);
    }

    public ApiResult<ResPageable> search(String categoryName, int page, int size) {
        Page<Category> search = categoryRepository.search(categoryName, PageRequest.of(page, size));
        List<CategoryDTO> categoryDTOList = search.getContent().stream().map(this::parseCategoryDTO).toList();
        return ApiResult.successResponse(ResPageable.builder()
                .page(page)
                .size(size)
                .totalPage(search.getTotalPages())
                .totalElements(search.getTotalElements())
                .body(categoryDTOList)
                .build());
    }
}
