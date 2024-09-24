package com.example.myapp.dto.request;

import jakarta.validation.constraints.NotBlank;

public record FolderCreate(
        @NotBlank(message = "This field is mandatory") String name, Long parentId
) {
}
