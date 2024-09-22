package com.example.myapp.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserProfile {
    private Long id;
    private String username;

    public UserProfile() {
    }

    public UserProfile(Long id, String username) {
        this.id = id;
        this.username = username;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                '}';
    }
}
