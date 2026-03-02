package com.example.demo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class EmployeeService {
    private EmployeeRepository repository;
    @Autowired // Spring will inject EmployeeRepository here
    public EmployeeService(EmployeeRepository repository) {
        this.repository = repository;
    }
    public void addEmployee(Employee emp) {
        repository.addEmployee(emp);
    }
    public void listEmployees() {
        for (Employee emp : repository.getAllEmployees()) {
            System.out.println(emp);
        }
    }
}