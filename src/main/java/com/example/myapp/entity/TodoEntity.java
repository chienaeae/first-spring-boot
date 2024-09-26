package com.example.myapp.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "todo")
public class TodoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;

    private String description;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private FolderEntity parentFolder;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    protected TodoEntity() {}

    public TodoEntity(String title, String description) {
        this.title = title;
        this.description = description;
    }

}
