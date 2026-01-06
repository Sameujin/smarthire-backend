package com.smarhire.config;

import com.smarhire.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            // 🔒 JWT = STATELESS
            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )

            .csrf(csrf -> csrf.disable())

            .authorizeHttpRequests(auth -> auth

                // 🔓 AUTH APIs
                .requestMatchers("/auth/**").permitAll()

                // 🔓 PUBLIC JOB LIST
                .requestMatchers(HttpMethod.GET, "/jobs/**").permitAll()

                // 🔐 RECRUITER
                .requestMatchers("/jobs/create").hasRole("RECRUITER")

                // 🔐 CANDIDATE
                .requestMatchers("/applications/**").hasRole("CANDIDATE")

                .anyRequest().authenticated()
            )

            // ✅ REGISTER JWT FILTER
            .addFilterBefore(
                jwtAuthenticationFilter,
                UsernamePasswordAuthenticationFilter.class
            );

        return http.build();
    }
}
