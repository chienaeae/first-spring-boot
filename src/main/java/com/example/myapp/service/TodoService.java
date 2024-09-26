package com.example.myapp.service;

import com.example.myapp.dto.request.TodoCreate;
import com.example.myapp.entity.UserEntity;
import com.example.myapp.entity.TodoEntity;
import com.example.myapp.model.Todo;
import com.example.myapp.repository.TodoRepository;
import com.example.myapp.service.mapper.FolderAndTodoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TodoService {
    private final TodoRepository todoRepository;

    private final FolderService folderService;

    private final FolderAndTodoMapper folderAndTodoMapper;

    @Autowired
    public TodoService(TodoRepository todoRepository, FolderService folderService, FolderAndTodoMapper folderAndTodoMapper) {
        this.todoRepository = todoRepository;
        this.folderService = folderService;
        this.folderAndTodoMapper = folderAndTodoMapper;
    }

    public Optional<Todo> createTodo(Long userId, TodoCreate body) {
        TodoEntity todo = new TodoEntity(body.title(), body.description());
        todo.setUser(new UserEntity(userId));
        todo.setTitle(body.title());
        todo.setDescription(body.description());
        folderService.putParentFolder(todo, body.parentId());
        return Optional
                .of(todoRepository.save(todo))
                .map(folderAndTodoMapper::toTodo);
    }
}
