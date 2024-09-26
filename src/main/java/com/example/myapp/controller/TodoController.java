package com.example.myapp.controller;

import com.example.myapp.dto.CurrentUser;
import com.example.myapp.dto.request.TodoCreate;
import com.example.myapp.exception.InternalException;
import com.example.myapp.model.Todo;
import com.example.myapp.model.User;
import com.example.myapp.service.AuthService;
import com.example.myapp.service.TodoService;
import com.example.myapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/todo")
public class TodoController {

    private final AuthService authService;
    private final TodoService todoService;

    @Autowired
    public TodoController(AuthService authService, TodoService todoService) {
        this.authService = authService;
        this.todoService = todoService;
    }

    @PostMapping("")
    public ResponseEntity<Todo> addTodo(@RequestBody @Validated TodoCreate body) {
        CurrentUser currentUser = authService.getCurrentUser();

        Optional<Todo> todo = todoService.createTodo(currentUser.userId(), body);
        return todo.map(ResponseEntity::ok)
                .orElseThrow(() -> new InternalException("Failed to create todo"));
    }
}
