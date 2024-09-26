package com.example.myapp.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Folder {
    private Long id;
    private String name;
    private LocalDateTime createdAt;
    private Long userId;

    private List<Todo> subTodos = new ArrayList<>();

    private List<Folder> subFolders = new ArrayList<>();

    public Folder() {

    }

    public Folder(String name) {

    }

    public void addTodo(Todo todo) {
        subTodos.add(todo);
    }

    public void addSubFolder(Folder folder) {
        subFolders.add(folder);
    }

    @Override
    public String toString() {
        return "Folder{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", createdAt=" + createdAt +
                ", todos=" + subTodos +
                ", subFolders=" + subFolders +
                '}';
    }
}
