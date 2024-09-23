package com.example.myapp.service.mapper;

import com.example.myapp.entity.RoleEntity;
import com.example.myapp.entity.UserEntity;
import com.example.myapp.model.Role;
import com.example.myapp.model.RoleEnum;
import com.example.myapp.model.UserProfile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "role", target = "role")
    UserProfile toUserProfile(UserEntity user);

    default RoleEnum toRole(RoleEntity roleEntity) {
        if (roleEntity == null) {
            return null;
        }
        return roleEntity.getTitle();
    }
}
