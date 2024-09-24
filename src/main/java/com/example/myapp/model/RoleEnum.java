package com.example.myapp.model;

import lombok.Getter;
import lombok.Setter;

@Getter
public enum RoleEnum {
    // Note: Enum key and value should be the same
    USER("USER"),
    USER_GUEST("USER_GUEST"),
    ADMIN("ADMIN"),
    SUPER_ADMIN("SUPER_ADMIN");

    private final String name;

    RoleEnum(String roleName) {
        this.name = roleName;
    }

}