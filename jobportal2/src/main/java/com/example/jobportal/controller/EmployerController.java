package com.example.jobportal.controller;

import com.example.jobportal.domain.Application;
import com.example.jobportal.domain.Job;
import com.example.jobportal.domain.User;
import com.example.jobportal.repository.ApplicationRepository;
import com.example.jobportal.repository.JobRepository;
import com.example.jobportal.repository.UserRepository;
import com.example.jobportal.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/employer")
public class EmployerController {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    @GetMapping("/dashboard")
    public String dashboard(Model model, Principal principal) {
        User employer = userRepository.findByUsername(principal.getName()).get();
        List<Job> postedJobs = jobRepository.findByEmployer(employer);
        
        // Simple dashboard analytics
        long totalApplications = postedJobs.stream().mapToLong(j -> j.getApplications().size()).sum();
        long shortlistedCandidates = postedJobs.stream()
            .flatMap(j -> j.getApplications().stream())
            .filter(a -> "SHORTLISTED".equals(a.getStatus()))
            .count();

        model.addAttribute("jobs", postedJobs);
        model.addAttribute("newJob", new Job());
        model.addAttribute("totalApplications", totalApplications);
        model.addAttribute("shortlistedCandidates", shortlistedCandidates);
        model.addAttribute("employer", employer);
        
        return "employer/dashboard";
    }

    @PostMapping("/job/post")
    public String postJob(@ModelAttribute("newJob") Job job, Principal principal) {
        User employer = userRepository.findByUsername(principal.getName()).get();
        job.setEmployer(employer);
        jobRepository.save(job);
        return "redirect:/employer/dashboard?success";
    }

    @GetMapping("/job/{id}/applicants")
    public String viewApplicants(@PathVariable Long id, Model model, Principal principal) {
        Optional<Job> jobOpt = jobRepository.findById(id);
        
        if (jobOpt.isPresent()) {
            Job job = jobOpt.get();
            // Validate if employer owns the job
            if (!job.getEmployer().getUsername().equals(principal.getName())) {
                return "redirect:/employer/dashboard";
            }
            model.addAttribute("job", job);
            model.addAttribute("applications", applicationRepository.findByJob(job));
            return "employer/applicants";
        }
        return "redirect:/employer/dashboard";
    }

    @PostMapping("/application/{id}/status")
    public String updateStatus(@PathVariable Long id, @RequestParam("status") String status, Principal principal) {
        Optional<Application> appOpt = applicationRepository.findById(id);
        
        if (appOpt.isPresent()) {
            Application application = appOpt.get();
            Job job = application.getJob();

            // Security check
            if (!job.getEmployer().getUsername().equals(principal.getName())) {
                return "redirect:/employer/dashboard";
            }

            application.setStatus(status.toUpperCase());
            applicationRepository.save(application);

            // Send notification if shortlisted
            if ("SHORTLISTED".equalsIgnoreCase(status)) {
                User student = application.getStudent();
                emailService.sendShortlistEmail(student.getEmail(), student.getFullName(), job.getTitle(), job.getEmployer().getCompanyName());
            }

            return "redirect:/employer/job/" + job.getId() + "/applicants";
        }
        return "redirect:/employer/dashboard";
    }
}
