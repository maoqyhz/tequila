package com.example.springdocker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SpringDockerApplication {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/")
    public User index() {
        RowMapper<User> rowMapper= new BeanPropertyRowMapper<>(User.class);
        User user = jdbcTemplate.queryForObject("select * from user where id = ?", rowMapper,19);
        return user;
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringDockerApplication.class, args);
    }

}
