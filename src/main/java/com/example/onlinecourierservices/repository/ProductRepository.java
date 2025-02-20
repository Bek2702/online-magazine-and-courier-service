package com.example.onlinecourierservices.repository;

import com.example.onlinecourierservices.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    boolean existsByName (String name);

}
