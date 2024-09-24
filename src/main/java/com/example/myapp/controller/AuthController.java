package com.example.myapp.controller;

import com.example.myapp.dto.UserAuthenticationResult;
import com.example.myapp.dto.request.AuthLogin;
import com.example.myapp.dto.request.AuthRefresh;
import com.example.myapp.dto.request.AuthSignup;
import com.example.myapp.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/guest")
    public ResponseEntity<UserAuthenticationResult> guest() {
        UserAuthenticationResult userAuthenticationResult = authService.signupGuest();
        return ResponseEntity.ok(userAuthenticationResult);
    }

    @PostMapping("/signup")
    public ResponseEntity<UserAuthenticationResult> signup(@RequestBody @Validated AuthSignup body) {
        UserAuthenticationResult userAuthenticationResult = authService.signup(body);
        return ResponseEntity.ok(userAuthenticationResult);
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
