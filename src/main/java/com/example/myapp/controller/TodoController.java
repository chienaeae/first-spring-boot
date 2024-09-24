package com.example.myapp.controller;

import com.example.myapp.dto.request.TodoCreate;
import com.example.myapp.model.Todo;
import com.example.myapp.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/todo")
public class TodoController {

    private final TodoService todoService;

    @Autowired
    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @PostMapping("")
    public ResponseEntity<Todo> addTodo(@RequestBody @Validated TodoCreate body) {
        Optional<Todo> todo = todoService.createTodo(body);
        return todo.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @GetMapping("")
    public ResponseEntity<List<Todo>> getAllTodos() {
        return ResponseEntity.ok(todoService.getTodos());
    }
}
