package com.example.myapp.service;

import com.example.myapp.dto.TodoRequest;
import com.example.myapp.entity.Todo;
import com.example.myapp.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class TodoService {
    @Autowired
    private TodoRepository todoRepository;

    public Optional<Todo> createTodo(TodoRequest todoRequest) {
        Todo todo = new Todo(
                todoRequest.getTitle(), todoRequest.getDescription());
        todo.setTitle(todoRequest.getTitle());
        todo.setDescription(todoRequest.getDescription());
        return Optional.of(
                todoRepository.save(todo)
        );
    }
}
