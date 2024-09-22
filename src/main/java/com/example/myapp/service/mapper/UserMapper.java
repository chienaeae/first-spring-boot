package com.example.myapp.service.mapper;

import com.example.myapp.entity.UserEntity;
import com.example.myapp.model.UserProfile;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserProfile toUserProfile(UserEntity user);
}
