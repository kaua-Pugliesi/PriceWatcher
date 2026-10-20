package com.example.pricewatcher.Service;

import com.example.pricewatcher.dto.request.CreateCategoryRequestDto;
import com.example.pricewatcher.dto.request.UpdateCategoryRequestDto;
import com.example.pricewatcher.dto.response.CategoryResponseDto;
import com.example.pricewatcher.model.Category;

import java.util.List;
import java.util.UUID;

public interface CategoryService {
    UUID create(Category category);

    List<Category> findAll();

    Category findById(UUID id);

    Category update(
            UUID id,
            Category category
    );

    void delete(UUID id);
}
