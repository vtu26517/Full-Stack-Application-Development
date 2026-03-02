package com.example.repository;
import org.springframework.stereotype.Repository;

@Repository
public class StudentRepository {
	  public String getStudentData() {
	        return "Data from Repository";
	    }
}


