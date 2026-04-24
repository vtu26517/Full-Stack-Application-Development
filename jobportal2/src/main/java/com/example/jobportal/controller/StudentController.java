package com.example.jobportal.controller;

import com.example.jobportal.domain.Application;
import com.example.jobportal.domain.Job;
import com.example.jobportal.domain.User;
import com.example.jobportal.repository.ApplicationRepository;
import com.example.jobportal.repository.JobRepository;
import com.example.jobportal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private UserRepository userRepository;

    public static String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "/uploads";

    @GetMapping("/dashboard")
    public String dashboard(@RequestParam(value = "search", required = false) String search, Model model, Principal principal) {
        User student = userRepository.findByUsername(principal.getName()).get();

        List<Job> jobs;
        if (search != null && !search.isEmpty()) {
            jobs = jobRepository.findByTitleContainingIgnoreCaseOrSkillsRequiredContainingIgnoreCase(search, search);
        } else {
            jobs = jobRepository.findAll();
        }

        List<Application> myApplications = applicationRepository.findByStudent(student);

        model.addAttribute("jobs", jobs);
        model.addAttribute("myApplications", myApplications);
        model.addAttribute("student", student);
        
        return "student/dashboard";
    }

    @PostMapping("/job/apply/{jobId}")
    public String applyForJob(@PathVariable Long jobId, 
                              @RequestParam("resume") MultipartFile file, 
                              Principal principal) {
        
        User student = userRepository.findByUsername(principal.getName()).get();
        Optional<Job> jobOpt = jobRepository.findById(jobId);
        
        if (jobOpt.isEmpty() || file.isEmpty()) {
            return "redirect:/student/dashboard?error";
        }
        
        Job job = jobOpt.get();
        
        // Check if already applied
        if (applicationRepository.findByStudentAndJob(student, job).isPresent()) {
            return "redirect:/student/dashboard?alreadyApplied";
        }

        // Save Custom Filename
        StringBuilder fileNames = new StringBuilder();
        Path uploadPath = Paths.get(UPLOAD_DIRECTORY);
        
        try {
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            
            String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            Path filePath = uploadPath.resolve(fileName);
            Files.write(filePath, file.getBytes());

            Application application = new Application();
            application.setJob(job);
            application.setStudent(student);
            application.setResumePath(fileName);
            applicationRepository.save(application);

        } catch (IOException e) {
            e.printStackTrace();
            return "redirect:/student/dashboard?error";
        }

        return "redirect:/student/dashboard?success";
    }
}
