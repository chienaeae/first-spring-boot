package com.example.myapp.service;

import com.example.myapp.entity.UserEntity;
import com.example.myapp.model.RoleEnum;
import com.example.myapp.model.UserProfile;
import com.example.myapp.repository.UserRepository;
import com.example.myapp.service.mapper.RoleMapper;
import com.example.myapp.service.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final UserMapper userMapper;

    @Autowired
    public UserService(UserRepository userRepository, RoleService roleService, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.userMapper = userMapper;
    }

    public Optional<UserProfile> getUserProfile() throws AuthenticationException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return userRepository.findByUsername(username)
                .map(userMapper::toUserProfile);
    }

    public boolean checkIfUserExists(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    public Optional<UserProfile> createUser(String username, String password, RoleEnum role) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(username);
        userEntity.setPassword(password);
        roleService.putRole(userEntity, role);

        return Optional.of(userRepository.save(userEntity))
                .map(userMapper::toUserProfile);
    }
}
