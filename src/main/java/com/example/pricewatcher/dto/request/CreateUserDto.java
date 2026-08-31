package com.example.pricewatcher.dto.request;

import jakarta.validation.constraints.NotBlank;

record CreateUserRequestDto(
        @NotBlank
        String email,
        @NotBlank
        String password,
        @NotBlank
        String name
) {
}
