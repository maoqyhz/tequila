package com.example.app.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Fururur
 * @create 2019-12-12-10:14
 */
@Slf4j
@RestController
@RequestMapping("api")
public class ApiController {

    @GetMapping("/query")
    public String query() {
        return "api";
    }
}
