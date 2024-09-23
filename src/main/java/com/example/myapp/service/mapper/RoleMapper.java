package com.example.myapp.service.mapper;

import com.example.myapp.entity.RoleEntity;
import com.example.myapp.model.Role;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    Role toRole(RoleEntity roleEntity);
    RoleEntity toEntity(Role role);
}
