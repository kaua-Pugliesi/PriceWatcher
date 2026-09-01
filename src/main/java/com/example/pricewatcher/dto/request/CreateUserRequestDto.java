package com.example.pricewatcher.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CreateUserRequestDto(
        @NotBlank
        String email,
        @NotBlank
        String password,
        @NotBlank
        String name

) {
}
