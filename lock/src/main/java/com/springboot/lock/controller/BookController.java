package com.springboot.lock.controller;

import com.springboot.lock.aop.LocalLock;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/books")
public class BookController {

    @LocalLock(key = "book:arg[0]")
    @GetMapping("/test")
    public String query(String token) {
        return "success - " +token;
    }
}
