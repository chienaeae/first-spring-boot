package com.example.myapp.controller;

import com.example.myapp.dto.UserAuthenticationResult;
import com.example.myapp.dto.request.AuthLogin;
import com.example.myapp.dto.request.AuthSignup;
import com.example.myapp.model.UserProfile;
import com.example.myapp.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.parameters.P;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<Void> signup(@RequestBody @Validated AuthSignup body) {
        try {
            Optional<UserProfile> userProfile = authService.signup(body);
            return userProfile.<ResponseEntity<Void>>map(profile -> ResponseEntity.created(
                            ServletUriComponentsBuilder
                                    .fromCurrentRequestUri()
                                    .path("/{id}")
                                    .buildAndExpand(profile.getUsername())
                                    .toUri()).build())
                    .orElseGet(() -> ResponseEntity.internalServerError().build());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<UserAuthenticationResult> login(@RequestBody @Validated AuthLogin body) {
        try {
            UserAuthenticationResult userAuthenticationResult = authService.authenticate(body);
            return ResponseEntity.ok(userAuthenticationResult);
        } catch (BadCredentialsException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/me")
    public ResponseEntity<UserProfile> me() {
        try {
            Optional<UserProfile> userProfile = authService.getUserProfile();
            return userProfile
                    .map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
