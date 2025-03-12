package com.example.onlinecourierservices.repository;

import com.example.onlinecourierservices.entity.Reviews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewsRepository extends JpaRepository<Reviews,Long> {

}
