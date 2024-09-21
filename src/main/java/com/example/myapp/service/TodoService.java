package com.example.myapp.service;

import com.example.myapp.dto.request.TodoCreate;
import com.example.myapp.entity.TodoEntity;
import com.example.myapp.model.Todo;
import com.example.myapp.repository.TodoRepository;
import com.example.myapp.service.mapper.TodoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
public class TodoService {
    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private TodoMapper todoMapper;

    public Optional<Todo> createTodo(TodoCreate body) {
        TodoEntity todoEntity = new TodoEntity(body.title(), body.description());
        todoEntity.setTitle(body.title());
        todoEntity.setDescription(body.description());
        return Optional
                .of(todoRepository.save(todoEntity))
                .map(entity -> todoMapper.toTodo(entity));
    }
}
