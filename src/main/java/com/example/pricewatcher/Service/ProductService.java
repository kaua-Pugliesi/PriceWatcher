package com.example.pricewatcher.Service;

import com.example.pricewatcher.model.Product;

import java.util.List;
import java.util.UUID;

public interface ProductService {

    UUID create(Product product);

    List<Product> findAll();

    Product findById(UUID id);

    Product update(UUID id, Product product);

    void delete(UUID id);

    List<Product> findByCategory(UUID categoryId);
}
