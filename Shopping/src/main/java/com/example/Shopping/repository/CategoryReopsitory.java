package com.example.Shopping.repository;

import com.example.Shopping.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryReopsitory extends JpaRepository<Category,Long> {
Optional<Category> findByName(String name);
}
