package com.example.pricewatcher.exception;

public class StoreAlreadyExistsException extends RuntimeException {
    public StoreAlreadyExistsException(String store){
        super("Store Already Exists: " + store );
    }
}
