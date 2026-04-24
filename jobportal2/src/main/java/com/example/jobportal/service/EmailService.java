package com.example.jobportal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Async
    public void sendShortlistEmail(String toEmail, String studentName, String jobTitle, String companyName) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("noreply@jobportal.com");
            message.setTo(toEmail);
            message.setSubject("Congratulations! You've been shortlisted for " + jobTitle);
            message.setText("Dear " + studentName + ",\n\n" +
                    "We are pleased to inform you that your application for the position of '" + jobTitle + 
                    "' at " + companyName + " has been shortlisted.\n\n" +
                    "The employer will contact you shortly with further details.\n\n" +
                    "Best Regards,\nJob Portal Team");

            mailSender.send(message);
            System.out.println("Email successfully sent to " + toEmail);
        } catch (Exception e) {
            System.err.println("Error while sending email: " + e.getMessage());
        }
    }
}
