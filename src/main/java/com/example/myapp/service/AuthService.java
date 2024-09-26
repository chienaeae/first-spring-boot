package com.example.myapp.service;

import com.example.myapp.dto.CurrentUser;
import com.example.myapp.dto.request.AuthLogin;
import com.example.myapp.dto.request.AuthSignup;
import com.example.myapp.dto.response.UserAuthenticationResponse;
import com.example.myapp.exception.BadException;
import com.example.myapp.exception.InternalException;
import com.example.myapp.model.RoleEnum;
import com.example.myapp.model.User;
import com.example.myapp.security.CustomUserDetails;
import com.example.myapp.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
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

    public UserAuthenticationResponse signupGuest() {
        Optional<User> newGuest = userService.createGuest();
        if (newGuest.isEmpty()) {
            throw new InternalException("Failed to create guest") {
            };
        }
        String username = newGuest.get().getUsername();
        return new UserAuthenticationResponse(
                username,
                jwtUtils.generateJwtAccessToken(username),
                jwtUtils.generateJwtRefreshToken(username));
    }

    public UserAuthenticationResponse signup(AuthSignup authSignup) throws BadException {
        boolean isUserExists = userService.checkIfUserExists(authSignup.username());
        if (isUserExists) {
            throw new BadException("Username is already taken") {
            };
        }

        // Try to finalize the guest user to the new user if the guest user exists
        CurrentUser guestUser = getCurrentUser();
        String encodedPassword = passwordEncoder.encode(authSignup.password());
        User newUser = userService
                .tryFinalizeUser(guestUser.username(), authSignup.username(), encodedPassword, RoleEnum.USER)
                .or(() -> userService.createUser(authSignup.username(), encodedPassword, RoleEnum.USER))
                .orElseThrow(() -> new InternalException("Failed to create user"));

        String username = newUser.getUsername();
        return new UserAuthenticationResponse(
                username,
                jwtUtils.generateJwtAccessToken(username),
                jwtUtils.generateJwtRefreshToken(username));
    }

    public UserAuthenticationResponse authenticate(AuthLogin authLogin) throws IllegalArgumentException {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authLogin.username(), authLogin.password())
            );
            String username = authentication.getName();
            return new UserAuthenticationResponse(
                    username,
                    jwtUtils.generateJwtAccessToken(username),
                    jwtUtils.generateJwtRefreshToken(username));
        } catch (AuthenticationException e) {
            throw new BadException("Invalid username/password supplied") {
            };
        }
    }

    public UserAuthenticationResponse refresh(String refreshToken) throws AuthenticationException {
        if (!jwtUtils.verifyJwtToken(refreshToken, JwtUtils.TokenType.REFRESH)) {
            throw new BadException("Invalid refresh token") {
            };
        }
        String username = jwtUtils.getUserNameFromJwtToken(refreshToken);
        return new UserAuthenticationResponse(
                username,
                jwtUtils.generateJwtAccessToken(username),
                jwtUtils.generateJwtRefreshToken(username));
    }

    public CurrentUser getCurrentUser() throws AuthenticationException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null && authentication.getPrincipal() instanceof CustomUserDetails userDetails) {
            return new CurrentUser(userDetails.getUserId(), userDetails.getUsername());
        }
        return null;
    }

}
