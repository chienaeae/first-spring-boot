package com.example.myapp.security;

import com.example.myapp.entity.UserEntity;
import com.example.myapp.model.Role;
import com.example.myapp.repository.UserRepository;
import com.example.myapp.service.mapper.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.List;

@Service
public class CustomUserDetailsService  implements UserDetailsService {
    private final UserRepository userRepository;

    private final RoleMapper roleMapper;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository, RoleMapper roleMapper) {
        this.userRepository = userRepository;
        this.roleMapper = roleMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> user = userRepository.findByUsername(username);
        if(user.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }

        return new CustomUserDetails(
                user.get().getId(),
                user.get().getUsername(),
                user.get().getPassword(),
                mapRoleToAuthorities(roleMapper.toRole(user.get().getRole()))
        );

    }

    private Collection<? extends GrantedAuthority> mapRoleToAuthorities(Role role) {
        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority(role.getTitle().getName()));
        return roles;
    }
}
