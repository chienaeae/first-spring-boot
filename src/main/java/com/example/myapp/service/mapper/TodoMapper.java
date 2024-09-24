package com.example.myapp.service.mapper;

import com.example.myapp.entity.TodoEntity;
import com.example.myapp.model.Todo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {FolderMapper.class})
public interface TodoMapper {
    Todo toTodo(TodoEntity todoEntity);

    List<Todo> toList(List<TodoEntity> todoEntityList);
}
