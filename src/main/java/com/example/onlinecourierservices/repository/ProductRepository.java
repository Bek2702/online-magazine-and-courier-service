package com.example.onlinecourierservices.repository;

import com.example.onlinecourierservices.entity.Product;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    boolean existsByName (String name);
    @Query(value = """
            SELECT p.available FROM product p where p.id = :productId
            """,nativeQuery = true)
    boolean findAvailableById(Long productId);

}
