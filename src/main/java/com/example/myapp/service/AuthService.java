package com.example.myapp.service;

import com.example.myapp.dto.request.AuthLogin;
import com.example.myapp.dto.request.AuthSignup;
import com.example.myapp.dto.UserAuthenticationResult;
import com.example.myapp.entity.UserEntity;
import com.example.myapp.exception.InvalidInputException;
import com.example.myapp.repository.UserRepository;
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
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    // 使用建構函數注入依賴
    @Autowired
    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       AuthenticationManager authenticationManager,
                       JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    public Optional<String> signup(AuthSignup authSignup) throws IllegalArgumentException{
        Optional<UserEntity> repeatedUser = userRepository.findByUsername(authSignup.username());
        if(repeatedUser.isPresent()) {
            throw new InvalidInputException("Username is already taken"){};
        }

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(authSignup.username());
        userEntity.setPassword(passwordEncoder.encode(authSignup.password()));

        return Optional
                .of(userRepository.save(userEntity))
                .map(UserEntity::getUsername);
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
