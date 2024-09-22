package com.example.myapp.controller;

import com.example.myapp.dto.UserAuthenticationResult;
import com.example.myapp.dto.request.AuthLogin;
import com.example.myapp.dto.request.AuthRefresh;
import com.example.myapp.dto.request.AuthSignup;
import com.example.myapp.model.UserProfile;
import com.example.myapp.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<Void> signup(@RequestBody @Validated AuthSignup body) {
        Optional<String> usernameOrNull = authService.signup(body);
        return usernameOrNull.<ResponseEntity<Void>>map(username -> ResponseEntity.created(
                        ServletUriComponentsBuilder
                                .fromCurrentRequestUri()
                                .path("/{username}")
                                .buildAndExpand(username)
                                .toUri()).build())
                .orElseGet(() -> ResponseEntity.internalServerError().build());
    }

    @PostMapping("/login")
    public ResponseEntity<UserAuthenticationResult> login(@RequestBody @Validated AuthLogin body) {
        UserAuthenticationResult userAuthenticationResult = authService.authenticate(body);
        return ResponseEntity.ok(userAuthenticationResult);
    }

    @PostMapping("/refresh")
    public ResponseEntity<UserAuthenticationResult> refresh(@RequestBody @Validated AuthRefresh body) {
        UserAuthenticationResult userAuthenticationResult = authService.refresh(body.refreshToken());
        return ResponseEntity.ok(userAuthenticationResult);
    }


}
