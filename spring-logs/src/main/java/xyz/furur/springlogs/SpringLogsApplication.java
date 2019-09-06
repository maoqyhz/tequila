package xyz.furur.springlogs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class SpringLogsApplication {
    private final Logger log = LoggerFactory.getLogger(SpringLogsApplication.class);

    @GetMapping("/{id}")
    public void foo(@PathVariable int id) {
        log.info("info log");
        log.error("error log");
        if (id == 1)
            throw new RuntimeException();
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringLogsApplication.class, args);
    }

}
