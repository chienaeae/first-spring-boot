package com.example.myapp.service.mapper;

import com.example.myapp.entity.TodoEntity;
import com.example.myapp.model.Todo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TodoMapperTest {
    private final TodoMapper todoMapper = Mappers.getMapper(TodoMapper.class);

    @Test
    @DisplayName("Test todoMapper.toTodo")
    public void testToTodo() {
        TodoEntity todoEntity = new TodoEntity("title", "description");
        Todo todo = todoMapper.toTodo(todoEntity);
        assertEquals(todoEntity.getTitle(), todo.getTitle());
        assertEquals(todoEntity.getDescription(), todo.getDescription());
    }

    @Test
    @DisplayName("Test todoMapper.toEntity")
    public void testToEntity() {
        Todo todo = new Todo();
        todo.setTitle("title");
        todo.setDescription("description");
        TodoEntity todoEntity = todoMapper.toEntity(todo);
        assertEquals(todo.getTitle(), todoEntity.getTitle());
        assertEquals(todo.getDescription(), todoEntity.getDescription());
    }
}
