package com.example.jobportal.repository;

import com.example.jobportal.domain.Job;
import com.example.jobportal.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobRepository extends JpaRepository<Job, Long> {
    List<Job> findByEmployer(User employer);
    List<Job> findByTitleContainingIgnoreCaseOrSkillsRequiredContainingIgnoreCase(String title, String skills);
}
