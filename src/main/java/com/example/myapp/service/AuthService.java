package com.example.myapp.service;

import com.example.myapp.dto.request.AuthLogin;
import com.example.myapp.dto.request.AuthSignup;
import com.example.myapp.dto.UserAuthenticationResult;
import com.example.myapp.exception.InvalidInputException;
import com.example.myapp.model.RoleEnum;
import com.example.myapp.model.UserProfile;
import com.example.myapp.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    // 使用建構函數注入依賴
    @Autowired
    public AuthService(UserService userService,
                       PasswordEncoder passwordEncoder,
                       AuthenticationManager authenticationManager,
                       JwtUtils jwtUtils) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    public Optional<String> signup(AuthSignup authSignup) throws IllegalArgumentException{
        boolean isUserExists = userService.checkIfUserExists(authSignup.username());
        if(isUserExists) {
            throw new InvalidInputException("Username is already taken"){};
        }

        Optional<UserProfile> newUser = userService.createUser(
                authSignup.username(),
                passwordEncoder.encode(authSignup.password()),
                RoleEnum.USER);

        if(newUser.isEmpty()) {
            throw new InvalidInputException("Failed to create user"){};
        }
        return newUser.map(UserProfile::getUsername);
    }

    public UserAuthenticationResult authenticate(AuthLogin authLogin) throws IllegalArgumentException {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authLogin.username(), authLogin.password())
            );
            String username = authentication.getName();
            return new UserAuthenticationResult(
                    username,
                    jwtUtils.generateJwtAccessToken(username),
                    jwtUtils.generateJwtRefreshToken(username));
        } catch (AuthenticationException e) {
            throw new InvalidInputException("Invalid username/password supplied") {};
        }
    }

    public UserAuthenticationResult refresh(String refreshToken) throws AuthenticationException{
        if(!jwtUtils.verifyJwtToken(refreshToken, JwtUtils.TokenType.REFRESH)){
            throw new InvalidInputException("Invalid refresh token"){};
        }
        String username = jwtUtils.getUserNameFromJwtToken(refreshToken);
        return new UserAuthenticationResult(
                username,
                jwtUtils.generateJwtAccessToken(username),
                jwtUtils.generateJwtRefreshToken(username));
    }

}
