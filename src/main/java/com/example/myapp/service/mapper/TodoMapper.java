package com.example.myapp.service.mapper;

import com.example.myapp.entity.TodoEntity;
import com.example.myapp.model.Todo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TodoMapper {
    Todo toTodo(TodoEntity todo);
    TodoEntity toEntity(Todo todo);
}
