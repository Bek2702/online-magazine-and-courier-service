package com.example.onlinecourierservices.repository;

import com.example.onlinecourierservices.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface  FileRepository extends JpaRepository<File,Long> {


}
