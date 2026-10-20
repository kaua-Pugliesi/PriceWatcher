package com.example.pricewatcher.dto.response;

import java.util.UUID;

public record ProductResponseDto(
        UUID id,
        String name,
        String description,
        String imageUrl,
        UUID categoryId,
        String categoryName
) {
}
