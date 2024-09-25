package com.example.myapp.dto.response;


public record UserAuthenticationResponse(
        String username,
        String accessToken,
        String refreshToken) {
}
