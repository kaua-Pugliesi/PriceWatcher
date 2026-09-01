package com.example.pricewatcher.dto.response;

import java.util.UUID;

public record UserResponseDto(
    UUID id,
    String Name,
    String Email
) {
}
