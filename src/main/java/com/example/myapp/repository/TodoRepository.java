package com.example.myapp.repository;

import com.example.myapp.entity.TodoEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface TodoRepository extends CrudRepository<TodoEntity, Long> {
    List<TodoEntity> findByTitle(String title);
    Optional<TodoEntity> findById(long id);
}
