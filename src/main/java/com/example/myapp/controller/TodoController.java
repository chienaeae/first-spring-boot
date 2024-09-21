package com.example.myapp.controller;

import com.example.myapp.dto.request.TodoCreate;
import com.example.myapp.model.Todo;
import com.example.myapp.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Optional;

@Controller
@RequestMapping("/todo")
public class TodoController {

    @Autowired
    private TodoService todoService;

    @PostMapping("")
    public @ResponseBody ResponseEntity<Todo> addTodo(@RequestBody TodoCreate body) {
        Optional<Todo> todo = todoService.createTodo(body);
        return todo.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }
}
