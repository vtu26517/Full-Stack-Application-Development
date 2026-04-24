package com.example.jobportal.repository;

import com.example.jobportal.domain.Application;
import com.example.jobportal.domain.Job;
import com.example.jobportal.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
    List<Application> findByStudent(User student);
    List<Application> findByJob(Job job);
    Optional<Application> findByStudentAndJob(User student, Job job);
}
