package com.example.pricewatcher.dto.request;

import jdk.jfr.Description;


public record RegisterRequestDto(

        String name,
        String email,
        String  password

) {
}
