package com.smarhire.repository;

import java.util.List;   // ✅ CORRECT IMPORT

import org.springframework.data.jpa.repository.JpaRepository;

import com.smarhire.entity.JobApplication;

public interface JobApplicationRepository
        extends JpaRepository<JobApplication, Long> {

    // Candidate dashboard
    List<JobApplication> findByCandidateId(Long candidateId);

    // Duplicate apply check
    boolean existsByJobIdAndCandidateId(Long jobId, Long candidateId);
}
