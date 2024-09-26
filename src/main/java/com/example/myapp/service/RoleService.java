package com.example.myapp.service;

import com.example.myapp.entity.RoleEntity;
import com.example.myapp.entity.UserEntity;
import com.example.myapp.exception.InternalException;
import com.example.myapp.model.RoleEnum;
import com.example.myapp.model.Role;
import com.example.myapp.repository.RoleRepository;
import com.example.myapp.service.mapper.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoleService {
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;
    private Map<RoleEnum, Role> roles = new HashMap<>();


    @Autowired
    public RoleService(RoleRepository roleRepository, RoleMapper roleMapper) {
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
    }

    public void loadRoles() {
        roles = roleRepository
                .findAll()
                .stream().map(roleMapper::toRole)
                .collect(Collectors.toMap(Role::getTitle, role -> role));
    }

    public void createRoleIfNotExists(RoleEnum title) {
        Optional<RoleEntity> role = roleRepository.findByTitle(title);
        if (role.isEmpty()) {
            RoleEntity newRole = new RoleEntity();
            newRole.setTitle(title);
            roleRepository.save(newRole);

            // Add the new role to the roles map
            roles.put(title, roleMapper.toRole(newRole));
        }
    }

    public void putRole(UserEntity userEntity, RoleEnum role) {
        RoleEntity roleEntity = Optional.ofNullable(roles.get(role))
                .map(roleMapper::toEntity)
                .orElseThrow(() -> new InternalException(String.format("Role: %s has not been created at startup", role.getName())));
        userEntity.setRole(roleEntity);
    }
}
