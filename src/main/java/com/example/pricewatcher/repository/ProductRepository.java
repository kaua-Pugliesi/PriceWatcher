package com.example.pricewatcher.repository;

import com.example.pricewatcher.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
    boolean existsByName(String name);
    List<Product> findByCategoryId(UUID categoryId);
}
