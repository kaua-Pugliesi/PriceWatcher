package com.example.pricewatcher.Service.impl;

import com.example.pricewatcher.Service.ProductService;
import com.example.pricewatcher.exception.ProductAlreadyExistsException;
import com.example.pricewatcher.exception.ProductNotFoundException;
import com.example.pricewatcher.model.Category;
import com.example.pricewatcher.model.Product;
import com.example.pricewatcher.repository.CategoryRepository;
import com.example.pricewatcher.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductServiceImpl(
            ProductRepository productRepository,
            CategoryRepository categoryRepository
    ) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public UUID create(Product product) {

        if (productRepository.existsByName(product.getName())) {
            throw new ProductAlreadyExistsException(product.getName());
        }

        if (product.getCategory() == null ||
                product.getCategory().getId() == null) {
            throw new IllegalArgumentException(
                    "O produto deve possuir uma categoria."
            );
        }

        Category category = categoryRepository
                .findById(product.getCategory().getId())
                .orElseThrow(() -> new RuntimeException(
                        "Categoria não encontrada."
                ));

        product.setCategory(category);

        return productRepository.save(product).getId();
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Product findById(UUID id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    @Override
    public Product update(UUID id, Product product) {

        Product existing = findById(id);

        boolean nameChanged =
                !existing.getName().equalsIgnoreCase(product.getName());

        if (nameChanged &&
                productRepository.existsByName(product.getName())) {
            throw new ProductAlreadyExistsException(product.getName());
        }

        if (product.getCategory() == null ||
                product.getCategory().getId() == null) {
            throw new IllegalArgumentException(
                    "O produto deve possuir uma categoria."
            );
        }

        Category category = categoryRepository
                .findById(product.getCategory().getId())
                .orElseThrow(() -> new RuntimeException(
                        "Categoria não encontrada."
                ));

        existing.setName(product.getName());
        existing.setDescription(product.getDescription());
        existing.setImageUrl(product.getImageUrl());
        existing.setCategory(category);

        return productRepository.save(existing);
    }

    @Override
    public void delete(UUID id) {

        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException(id);
        }

        productRepository.deleteById(id);
    }

    @Override
    public List<Product> findByCategory(UUID categoryId) {

        if (!categoryRepository.existsById(categoryId)) {
            throw new RuntimeException("Categoria não encontrada.");
        }

        return productRepository.findByCategoryId(categoryId);
    }
}
