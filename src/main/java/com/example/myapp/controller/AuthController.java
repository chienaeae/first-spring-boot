package com.example.myapp.controller;

import com.example.myapp.dto.response.UserAuthenticationResponse;
import com.example.myapp.dto.request.AuthLogin;
import com.example.myapp.dto.request.AuthRefresh;
import com.example.myapp.dto.request.AuthSignup;
import com.example.myapp.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/guest")
    public ResponseEntity<UserAuthenticationResponse> guest() {
        UserAuthenticationResponse userAuthenticationResult = authService.signupGuest();
        return ResponseEntity.ok(userAuthenticationResult);
    }

    @PostMapping("/signup")
    public ResponseEntity<UserAuthenticationResponse> signup(@RequestBody @Validated AuthSignup body) {
        UserAuthenticationResponse userAuthenticationResult = authService.signup(body);
        return ResponseEntity.ok(userAuthenticationResult);
    }

    @PostMapping("/login")
    public ResponseEntity<UserAuthenticationResponse> login(@RequestBody @Validated AuthLogin body) {
        UserAuthenticationResponse userAuthenticationResult = authService.authenticate(body);
        return ResponseEntity.ok(userAuthenticationResult);
    }

    @PostMapping("/refresh")
    public ResponseEntity<UserAuthenticationResponse> refresh(@RequestBody @Validated AuthRefresh body) {
        UserAuthenticationResponse userAuthenticationResult = authService.refresh(body.refreshToken());
        return ResponseEntity.ok(userAuthenticationResult);
    }


}
