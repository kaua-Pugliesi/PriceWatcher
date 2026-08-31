package com.example.pricewatcher.dto.request;

import jakarta.validation.constraints.NotBlank;

public record UpdateUserRequestDto(
        @NotBlank
        String email,
        @NotBlank
        String password,
        @NotBlank
        String name
) {

}
