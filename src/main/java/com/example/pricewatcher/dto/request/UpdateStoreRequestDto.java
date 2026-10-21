package com.example.pricewatcher.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UpdateStoreRequestDto(

        @NotBlank(message = "O nome da loja é obrigatório")
        String name,

        @NotBlank(message = "A URL da loja é obrigatória")
        @Pattern(
                regexp = "^(https?:// ).+",
                message = "A URL deve começar com http:// ou https://"
        )
        String websiteUrl
) {
}
