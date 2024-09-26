package com.example.myapp.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "folder")
public class FolderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "parentFolder", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    private List<TodoEntity> subTodos = new ArrayList<>();

    @OneToMany(mappedBy = "parentFolder", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    private List<FolderEntity> subFolders = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private FolderEntity parentFolder;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    protected FolderEntity() {}

    public FolderEntity(String name) {
        this.name = name;
    }

    public void addSubFolder(FolderEntity folder) {
        subFolders.add(folder);
        folder.setParentFolder(this);
    }

    public void addSubTodo(TodoEntity todo) {
        subTodos.add(todo);
        todo.setParentFolder(this);
    }
}
