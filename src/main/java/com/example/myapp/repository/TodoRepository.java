package com.example.myapp.repository;

import com.example.myapp.entity.Todo;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface TodoRepository extends CrudRepository<Todo, Long> {
    List<Todo> findByTitle(String title);
    Optional<Todo> findById(long id);
}
