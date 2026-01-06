package com.smarhire.controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smarhire.entity.JobApplication;
import com.smarhire.model.User;
import com.smarhire.repository.UserRepository;
import com.smarhire.service.JobApplicationService;

@RestController
@RequestMapping("/applications")
public class JobApplicationController {

    private final JobApplicationService jobApplicationService;
    private final UserRepository userRepository;

    public JobApplicationController(JobApplicationService jobApplicationService,
                                    UserRepository userRepository) {
        this.jobApplicationService = jobApplicationService;
        this.userRepository = userRepository;
    }

    // ✅ APPLY JOB
    @PostMapping("/jobs/{jobId}/apply")
    public String applyJob(@PathVariable Long jobId,
                           Authentication authentication) {

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        jobApplicationService.applyJob(jobId, user.getId());

        return "Job applied successfully";
    }

    // ✅ CANDIDATE DASHBOARD
    @GetMapping("/my")
    public List<JobApplication> getMyApplications(Authentication authentication) {

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return jobApplicationService.getMyApplications(user.getId());
    }
}
