package com.example.myapp.dto;

import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.NotBlank;

@Getter
@Setter
public class TodoRequest {
    @NotBlank(message = "Title is mandatory")
    private String title;

    private String description;
}
