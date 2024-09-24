package com.example.myapp.service;

import com.example.myapp.entity.UserEntity;
import com.example.myapp.model.RoleEnum;
import com.example.myapp.model.User;
import com.example.myapp.repository.UserRepository;
import com.example.myapp.service.mapper.UserMapper;
import com.example.myapp.util.Base62IDGenerator;
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
    private final Base62IDGenerator base62IDGenerator;

    @Autowired
    public UserService(UserRepository userRepository, RoleService roleService, UserMapper userMapper, Base62IDGenerator base62IDGenerator) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.userMapper = userMapper;
        this.base62IDGenerator = base62IDGenerator;
    }

    public Optional<User> getUser() throws AuthenticationException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return userRepository.findByUsername(username)
                .map(userMapper::toUser);
    }

    public boolean checkIfUserExists(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    public Optional<User> createUser(String username, String password, RoleEnum role) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(username);
        userEntity.setPassword(password);
        roleService.putRole(userEntity, role);

        return Optional.of(userRepository.save(userEntity))
                .map(userMapper::toUser);
    }

    public Optional<User> createGuest() {
        UserEntity userEntity = new UserEntity();
        String username = base62IDGenerator.generateID();
        userEntity.setUsername(username);
        userEntity.setPassword("");
        roleService.putRole(userEntity, RoleEnum.USER_GUEST);

        return Optional.of(userRepository.save(userEntity))
                .map(userMapper::toUser);
    }
}
