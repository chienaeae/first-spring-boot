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

}
