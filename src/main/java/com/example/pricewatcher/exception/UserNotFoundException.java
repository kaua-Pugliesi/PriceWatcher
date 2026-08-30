package com.example.pricewatcher.exception;

import java.util.UUID;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(UUID uuid){
        super("User not found related to ID: " + uuid );
    }
}
