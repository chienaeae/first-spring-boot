package com.example.myapp.dto.request;

import jakarta.validation.constraints.NotBlank;

public record AuthRefresh (
        @NotBlank(message = "This field is mandatory") String refreshToken
) {
}
