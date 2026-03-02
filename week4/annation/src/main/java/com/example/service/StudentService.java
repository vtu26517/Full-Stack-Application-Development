package com.example.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.repository.StudentRepository;

@Service
public class StudentService {
    @Autowired
    private StudentRepository repository;

    public String getStudentInfo() {
        return "Student Name: Rahul | "
                + repository.getStudentData();
    }
}