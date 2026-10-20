package com.example.pricewatcher.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CreateProductRequestDto(
        @NotBlank
        String name,

        String description,

        String imageUrl,

        @NotNull
        UUID categoryId

) {
}
