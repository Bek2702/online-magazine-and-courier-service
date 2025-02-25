package com.example.onlinecourierservices.repository;

import com.example.onlinecourierservices.entity.Order;
import com.example.onlinecourierservices.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {
    Optional<Order> findByUserId(User userId);
}
