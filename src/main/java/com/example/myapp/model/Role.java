package com.example.myapp.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Role {
    private Long id;
    private RoleEnum title;

    public Role() {
    }

    public Role(Long id, RoleEnum title) {
        this.id = id;
        this.title = title;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }
}

