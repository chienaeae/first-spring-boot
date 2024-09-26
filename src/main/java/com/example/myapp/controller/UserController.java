package com.example.myapp.controller;

import com.example.myapp.dto.CurrentUser;
import com.example.myapp.exception.CustomNotFoundException;
import com.example.myapp.model.User;
import com.example.myapp.service.AuthService;
import com.example.myapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController()
@RequestMapping("/user")
public class UserController {
    private final AuthService authService;
    private final UserService userService;

    @Autowired
    public UserController(AuthService authService, UserService userService) {
        this.authService = authService;
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<User> me() {
        CurrentUser currentUser = authService.getCurrentUser();
        Optional<User> user = userService.getUser(currentUser.username());
        return user
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new CustomNotFoundException("User not found"));
    }
}
