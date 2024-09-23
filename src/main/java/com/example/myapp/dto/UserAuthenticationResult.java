package com.example.myapp.dto;


public record UserAuthenticationResult(
        String username,
        String accessToken,
        String refreshToken) {
}
