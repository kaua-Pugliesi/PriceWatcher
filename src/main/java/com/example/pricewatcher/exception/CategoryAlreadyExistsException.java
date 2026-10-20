package com.example.pricewatcher.exception;

public class CategoryAlreadyExistsException extends RuntimeException {
    public CategoryAlreadyExistsException(String category){
        super("Category Already Exists: " + category );
    }
}
