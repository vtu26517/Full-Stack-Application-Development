package com.example.demo.service;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.demo.student;
import com.example.demo.repository.StudentRepository;

@Service
public class StudentService {

    private final StudentRepository repository;

    public StudentService(StudentRepository repository) {
        this.repository = repository;
    }

    // ✅ CREATE
    public student saveStudent(student student) {
        return repository.save(student);
    }

    // ✅ READ ALL
    public List<student> getAllStudents() {
        return repository.findAll();
    }

    // ✅ READ BY ID
    public student getStudentById(Long id) {
        return repository.findById(id).orElse(null);
    }

    // ✅ UPDATE
    public student updateStudent(Long id, student student) {
        student existing = repository.findById(id).orElse(null);
        if (existing != null) {
            existing.setName(student.getName());
            existing.setAge(student.getAge());
            existing.setDepartment(student.getDepartment());
            return repository.save(existing);
        }
        return null;
    }

    // ✅ DELETE
    public void deleteStudent(Long id) {
        repository.deleteById(id);
    }

    // ===============================
    // 🔹 TASK 5.4 – Custom Queries
    // ===============================

    public List<student> getStudentsByDepartment(String department) {
        return repository.findByDepartment(department);
    }

    public List<student> getStudentsByAgeGreaterThan(int age) {
        return repository.findByAgeGreaterThan(age);
    }

    // ===============================
    // 🔹 TASK 5.5 – Sorting
    // ===============================

    public List<student> getStudentsSortedByName() {
        return repository.findAll(Sort.by(Sort.Direction.ASC, "name"));
    }

    // ===============================
    // 🔹 TASK 5.5 – Pagination
    // ===============================

    public Page<student> getStudentsWithPagination(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return repository.findAll(pageable);
    }
}
