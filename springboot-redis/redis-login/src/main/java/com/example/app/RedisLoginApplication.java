package com.example.app;

import com.example.app.common.repo.BaseRepositoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "com.example.app",
        repositoryBaseClass = BaseRepositoryImpl.class)
@SpringBootApplication
public class RedisLoginApplication {

    public static void main(String[] args) {
        SpringApplication.run(RedisLoginApplication.class, args);
    }
}
