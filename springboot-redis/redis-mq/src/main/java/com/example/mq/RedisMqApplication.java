package com.example.mq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class RedisMqApplication {

    public static void main(String[] args) {
        SpringApplication.run(RedisMqApplication.class, args);
    }

}
