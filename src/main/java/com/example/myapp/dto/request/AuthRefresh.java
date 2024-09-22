package com.example.myapp.dto.request;

import jakarta.validation.constraints.NotBlank;

public record AuthRefresh (
        @NotBlank String refreshToken
) {
}
