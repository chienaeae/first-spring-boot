package com.example.myapp.service;

import com.example.myapp.dto.request.TodoCreate;
import com.example.myapp.entity.TodoEntity;
import com.example.myapp.model.Todo;
import com.example.myapp.repository.TodoRepository;
import com.example.myapp.service.mapper.TodoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class TodoService {


    private TodoRepository todoRepository;

    private TodoMapper todoMapper;

    private FolderService folderService;

    @Autowired
    public TodoService(TodoRepository todoRepository, TodoMapper todoMapper, FolderService folderService) {
        this.todoRepository = todoRepository;
        this.todoMapper = todoMapper;
        this.folderService = folderService;
    }

    public Optional<Todo> createTodo(TodoCreate body) {
        TodoEntity todo = new TodoEntity(body.title(), body.description());
        todo.setTitle(body.title());
        todo.setDescription(body.description());
        folderService.putParentFolder(todo, body.parentId());
        return Optional
                .of(todoRepository.save(todo))
                .map(entity -> todoMapper.toTodo(entity));
    }

    public List<Todo> getTodos() {
        return StreamSupport.stream(
                        todoRepository.findAll().spliterator(), false
                ).map(todoMapper::toTodo)
                .collect(Collectors.toList());
    }
}
