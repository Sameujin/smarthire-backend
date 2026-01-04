package com.smarhire.controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.smarhire.model.Job;
import com.smarhire.service.JobService;

@RestController
@RequestMapping("/jobs")
public class JobController {

    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    // ✅ Only RECRUITER can create job
    @PostMapping("/create")
    public Job createJob(@RequestBody Job job,
                         Authentication authentication) {

        String recruiterEmail = authentication.getName();
        return jobService.createJob(job, recruiterEmail);
    }

    // ✅ Public job listing
    @GetMapping
    public List<Job> getAllJobs() {
        return jobService.getAllJobs();
    }
}
