package com.example.pricewatcher.exception;

import java.util.UUID;

public class StoreNotFoundException extends RuntimeException {
    public StoreNotFoundException(UUID uuid){
        super("Store not found related to ID: " + uuid );
    }
}
