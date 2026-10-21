package com.example.pricewatcher.dto.response;

import java.util.UUID;

public record StoreResponseDto(
        UUID id,
        String name,
        String websiteUrl
) {
}
