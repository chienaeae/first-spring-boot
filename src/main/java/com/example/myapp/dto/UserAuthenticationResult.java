package com.example.myapp.dto;

import com.example.myapp.model.UserProfile;

public record UserAuthenticationResult(
        String username,
        String token) {
}
