package com.smarhire.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.smarhire.entity.ApplicationStatus;
import com.smarhire.entity.JobApplication;
import com.smarhire.repository.JobApplicationRepository;

@Service
public class JobApplicationServiceImp implements JobApplicationService {

    private final JobApplicationRepository jobApplicationRepository;

    public JobApplicationServiceImp(JobApplicationRepository jobApplicationRepository) {
        this.jobApplicationRepository = jobApplicationRepository;
    }

    @Override
    public void applyJob(Long jobId, Long candidateId) {
        if (jobApplicationRepository
                .existsByJobIdAndCandidateId(jobId, candidateId)) {
            throw new RuntimeException("Already applied");
        }

        JobApplication application = new JobApplication();
        application.setJobId(jobId);
        application.setCandidateId(candidateId);
        application.setStatus(ApplicationStatus.APPLIED);

        jobApplicationRepository.save(application);
    }

    // 🔴 MUST MATCH INTERFACE + CONTROLLER
    @Override
    public List<JobApplication> getMyApplications(Long candidateId) {
        return jobApplicationRepository.findByCandidateId(candidateId);
    }
}
