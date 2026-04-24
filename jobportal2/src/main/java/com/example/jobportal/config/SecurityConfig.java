package com.example.jobportal.config;

import com.example.jobportal.security.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/", "/register", "/login", "/css/**", "/js/**", "/images/**", "/h2-console/**", "/uploads/**").permitAll()
                .requestMatchers("/student/**").hasAuthority("ROLE_STUDENT")
                .requestMatchers("/employer/**").hasAuthority("ROLE_EMPLOYER")
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .usernameParameter("username")
                .successHandler((request, response, authentication) -> {
                    boolean isEmployer = authentication.getAuthorities().stream()
                            .anyMatch(a -> a.getAuthority().equals("ROLE_EMPLOYER"));
                    if (isEmployer) {
                        response.sendRedirect("/employer/dashboard");
                    } else {
                        response.sendRedirect("/student/dashboard");
                    }
                })
                .permitAll()
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/")
                .permitAll()
            )
            // Needed for H2 Console
            .csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**"))
            .headers(headers -> headers.frameOptions(frame -> frame.disable()));
        
        return http.build();
    }
}
