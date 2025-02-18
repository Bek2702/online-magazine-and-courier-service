package com.example.onlinecourierservices.repository;

import com.example.onlinecourierservices.entity.Category;
import com.example.onlinecourierservices.payload.CategoryDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(String name);
//    Optional<Category> findByParentCategory(Category parentCategory);

    List<Category> findByParentCategory(Category parentCategory);
//    @Query("select c.name from Category c where c.parentCategory is null")
    List<Category> findAllByParentCategoryIsNull();

    boolean existsByName(String name);

}
