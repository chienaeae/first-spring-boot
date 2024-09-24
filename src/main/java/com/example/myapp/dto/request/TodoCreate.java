package com.example.myapp.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TodoCreate (
        @NotBlank(message = "This field is mandatory")String title,
        String description,
        @NotNull(message = "Every Todo must belong to a folder") Long parentId
) {}
