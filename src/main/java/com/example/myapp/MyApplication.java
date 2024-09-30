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

}