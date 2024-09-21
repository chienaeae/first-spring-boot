package com.example.myapp.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

public record TodoCreate (
        @NotBlank(message = "Title is mandatory")String title, String description
) {}
