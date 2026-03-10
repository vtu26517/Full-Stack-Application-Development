package com.example.demo;

import jakarta.persistence.*;

@Entity
@Table(name = "students")
public class student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "student_name", nullable = false)
    private String name;

    private int age;

    private String department;

    // Default Constructor
    public student() {}

    // Parameterized Constructor
    public student(String name, int age, String department) {
        this.name = name;
        this.age = age;
        this.department = department;
    }

    // ✅ CORRECT GETTERS

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getDepartment() {
        return department;
    }

    // ✅ CORRECT SETTERS

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}