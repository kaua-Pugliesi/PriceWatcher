package com.example.pricewatcher.exception;

import java.util.UUID;

public class CategoryNotFoundException extends RuntimeException {
    public CategoryNotFoundException(UUID uuid){
        super("Category not found related to ID: " + uuid );
    }
}
