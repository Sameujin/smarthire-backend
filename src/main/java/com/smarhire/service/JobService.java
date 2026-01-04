package com.smarhire.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.smarhire.model.Job;
import com.smarhire.repository.JobRepository;

@Service
public class JobService {

    private final JobRepository jobRepository;

    public JobService(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    public Job createJob(Job job, String recruiterEmail) {
        job.setRecruiterEmail(recruiterEmail);
        return jobRepository.save(job);
    }

    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }
}
