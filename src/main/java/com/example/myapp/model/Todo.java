package com.example.myapp.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Todo {
    private Long id;
    private String title;
    private String description;

    public Todo() {
    }

    public Todo(String title, String description) {
        this.title = title;
        this.description = description;
    }

    @Override
    public String toString() {
        return "Todo{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
