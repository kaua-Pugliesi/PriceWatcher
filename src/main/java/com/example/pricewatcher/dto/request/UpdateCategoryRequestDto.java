package com.example.pricewatcher.dto.request;

import jakarta.validation.constraints.NotBlank;

public record UpdateCategoryRequestDto(
        @NotBlank
        String name,

        String description
) {
}
