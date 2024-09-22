package com.example.myapp.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AuthSignup(
        @NotBlank(message = "This field is mandatory") String username,
        @NotBlank(message = "This field is mandatory") String password
) {
}
