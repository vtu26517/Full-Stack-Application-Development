package com.example.demo;
import org.springframework.web.bind.annotation.*;
@RestController
public class Api {
    @GetMapping("/")
    public String home() {
        return "Welcome Reshu! Simple REST API is running!";
    }
    @GetMapping("/hello/{name}")
    public String hello(@PathVariable String name) {
        return "Hello " + name;
    }
    @PostMapping("/add")
    public String add(@RequestBody String name) {
        return "Added: " + name;
    }    
    @DeleteMapping("/delete/{name}")
    public String delete(@PathVariable String name) {
        return "Deleted: " + name;
    }
}