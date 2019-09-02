package com.example.springdocker;

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

    private final JdbcTemplate jdbcTemplate;

    public SpringDockerApplication(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping("/")
    public Message index() {
        RowMapper<Message> rowMapper= new BeanPropertyRowMapper<>(Message.class);
        Message msg = jdbcTemplate.queryForObject("select * from message where k = ?", rowMapper,"msg");
        return msg;
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringDockerApplication.class, args);
    }
}
