package com.example.onlinecourierservices.repository;

import com.example.onlinecourierservices.entity.Product;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsByName(String name);

    @Query(value = """
            SELECT p.available FROM product p where p.id = :productId
            """, nativeQuery = true)
    boolean findAvailableById(Long productId);

    @Query(value = """
                select p.*
                FROM product p
                where (:productName IS NULL OR LOWER(p.name) like LOWER(CONCAT('%', :productName, '%')))
                  and (:categoryId IS NULL OR p.category_id = :categoryId)
                  and (:startPrice IS NULL OR p.price >= cast(:startPrice as double precision))
                  and (:endPrice IS NULL OR p.price <= cast(:endPrice as double precision))
            """, nativeQuery = true)
    Page<Product> searchGroup(String productName, Long categoryId, Double startPrice, Double endPrice, Pageable pageable);
}
