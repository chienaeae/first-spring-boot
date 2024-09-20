package com.example.myapp.controller;

import com.example.myapp.dto.TodoRequest;
import com.example.myapp.entity.Todo;
import com.example.myapp.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public @ResponseBody String addTodo(@RequestBody TodoRequest todoRequest) {
        Optional<Todo> todo = todoService.createTodo(todoRequest);
        if(todo.isPresent()) {
            return "Todo created with id: " + todo.get().getId();
        }
        return "Failed to create todo";
    }
}
