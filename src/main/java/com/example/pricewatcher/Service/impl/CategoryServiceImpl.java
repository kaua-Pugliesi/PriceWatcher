package com.example.pricewatcher.Service.impl;

import com.example.pricewatcher.Service.CategoryService;
import com.example.pricewatcher.exception.CategoryAlreadyExistsException;
import com.example.pricewatcher.exception.CategoryNotFoundException;
import com.example.pricewatcher.model.Category;
import com.example.pricewatcher.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;

    @Override
    public UUID create(Category category) {
        if (categoryRepository.existsByName(category.getName())){
            throw new CategoryAlreadyExistsException(category.getName());
        }

        return categoryRepository.save(category).getId();
    }

    @Override
    public List<Category> findAll() {
        return  categoryRepository.findAll();
    }

    @Override
    public Category findById(UUID id) {
        return categoryRepository.findById(id).orElseThrow ( () -> new RuntimeException("Categoria não encontrada."));
    }

    @Override
    public Category update(UUID id, Category category) {
        Category existing = findById(id);
        if(!existing.getName().equals(category.getName()) && categoryRepository.existsByName(category.getName()) )  {
            throw new CategoryAlreadyExistsException(category.getName());
        }
        existing.setName(category.getName());
        existing.setDescription(category.getDescription());
        return categoryRepository.save(existing);
    }

    @Override
    public void delete(UUID id) {
        if(!categoryRepository.existsById(id)){
            throw new CategoryNotFoundException(id);
        }
        categoryRepository.deleteById(id);
    }
}
