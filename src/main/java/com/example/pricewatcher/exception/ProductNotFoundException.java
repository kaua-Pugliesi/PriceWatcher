package com.example.pricewatcher.exception;

import java.util.UUID;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(UUID uuid){
        super("Product not found related to ID: " + uuid );
    }
}
