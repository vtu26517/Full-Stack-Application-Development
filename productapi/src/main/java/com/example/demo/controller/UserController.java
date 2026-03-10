package com.example.demo.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import jakarta.validation.Valid;

import com.example.demo.model.User;

@RestController

public class UserController {

    @PostMapping("/register")

    public ResponseEntity<User> register(@Valid @RequestBody User user){

        return ResponseEntity.ok(user);
    }

    @PostMapping("/login")

    public String login(@RequestParam String username,
                        @RequestParam String password){

        if(username.equals("admin") && password.equals("1234")){
            return "JWT Token Generated";
        }

        return "Invalid Credentials";
    }
}