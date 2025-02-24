package com.example.onlinecourierservices.repository;

import com.example.onlinecourierservices.entity.Category;
import com.example.onlinecourierservices.payload.CategoryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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

    @Query(value = """
            select c.* from category c where 
            (:categoryName is null or lower(c.name) like lower(concat('%', :categoryName,'%')))
            """, nativeQuery = true)
    Page<Category> search(String categoryName, Pageable pageable);
}
