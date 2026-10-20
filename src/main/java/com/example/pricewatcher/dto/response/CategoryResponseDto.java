package com.example.pricewatcher.dto.response;

import java.util.UUID;

public record CategoryResponseDto(

        UUID id,
        String name,
        String description

) {
}
