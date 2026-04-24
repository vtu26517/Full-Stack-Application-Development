package com.example.jobportal.controller;

import com.example.jobportal.domain.User;
import com.example.jobportal.dto.UserRegistrationDto;
import com.example.jobportal.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("userDto", new UserRegistrationDto());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("userDto") UserRegistrationDto userDto, 
                               BindingResult result, 
                               Model model) {
        
        if (result.hasErrors()) {
            return "register";
        }

        if (userRepository.findByUsername(userDto.getUsername()).isPresent()) {
            result.rejectValue("username", null, "There is already an account registered with that username");
            return "register";
        }

        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setFullName(userDto.getFullName());
        
        // Either ROLE_STUDENT or ROLE_EMPLOYER
        user.setRole("ROLE_" + userDto.getRole().toUpperCase());
        
        if ("EMPLOYER".equalsIgnoreCase(userDto.getRole())) {
            user.setCompanyName(userDto.getCompanyName());
        }

        userRepository.save(user);

        return "redirect:/login?success";
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }
}
