package com.example.myapp;

import com.example.myapp.entity.TodoEntity;
import com.example.myapp.repository.TodoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Optional;

@SpringBootApplication
public class MyApplication {
    private static final Logger log = LoggerFactory.getLogger(
            MyApplication.class
    );

    public static void main(String[] args) {
        SpringApplication.run(MyApplication.class, args);
    }

//    @Bean
//    public CommandLineRunner demo(TodoRepository repository) {
//        return (args) -> {
//            repository.save(new TodoEntity("Fanish this project", "Finish this project"));
//            repository.save(new TodoEntity("Play games", "Play games"));
//            repository.save(new TodoEntity("Watch movies", "Watch movies"));
//            repository.save(new TodoEntity("Read books", "Read books"));
//            repository.save(new TodoEntity("Go to the gym", "Go to the gym"));
//            repository.save(new TodoEntity("Go to the beach", "Go to the beach"));
//            repository.save(new TodoEntity("Go to the park", "Go to the park"));
//            repository.save(new TodoEntity("Go to the mall", "Go to the mall"));
//            repository.save(new TodoEntity("Go to the zoo", "Go to the zoo"));
//            repository.save(new TodoEntity("Go to the museum", "Go to the museum"));
//            repository.save(new TodoEntity("Go to the library", "Go to the library"));
//            repository.save(new TodoEntity("Go to the cinema", "Go to the cinema"));
//            repository.save(new TodoEntity("Go to the restaurant", "Go to the restaurant"));
//            repository.save(new TodoEntity("Go to the cafe", "Go to the cafe"));
//            repository.save(new TodoEntity("Go to the bar", "Go to the bar"));
//            repository.save(new TodoEntity("Go to the club", "Go to the club"));
//
//            log.info("Todos found with findAll():");
//            log.info("-------------------------------");
//            repository.findAll().forEach(todo -> {
//                log.info(todo.toString());
//            });
//            log.info("");
//
//            Optional<TodoEntity> todo = repository.findById(1L);
//            log.info("Todo found with findById(1L):");
//            log.info("--------------------------------");
//            if(todo.isPresent()) {
//                log.info(todo.toString());
//            } else {
//                log.info("Todo not found");
//            }
//            log.info("");
//
//            log.info("Todo found with findByTitle('Go to'):");
//            log.info("--------------------------------");
//            repository.findByTitle("Go to").forEach(goTo -> {
//                log.info(goTo.toString());
//            });
//            log.info("");
//        };
//    }
}