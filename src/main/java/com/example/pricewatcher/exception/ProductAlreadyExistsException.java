package com.example.pricewatcher.exception;

public class ProductAlreadyExistsException extends RuntimeException {
    public ProductAlreadyExistsException(String product){
        super("Product Already Exists: " + product );
    }
}
