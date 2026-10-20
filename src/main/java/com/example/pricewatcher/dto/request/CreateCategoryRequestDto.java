package com.example.pricewatcher.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CreateCategoryRequestDto(
        @NotBlank
        String name,

        String description
) {
}
