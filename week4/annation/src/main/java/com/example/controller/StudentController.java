package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.service.StudentService;
import com.example.component.CourseComponent;

@RestController
public class StudentController {

    @Autowired
    private StudentService service;

    @Autowired
    private CourseComponent component;

    @Autowired
    private String customMessage;

    @GetMapping("/student")
    public String showStudent() {
        return service.getStudentInfo() 
                + " | " 
                + component.getCourse()
                + " | "
                + customMessage;
    }
}