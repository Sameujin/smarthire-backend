package com.smarhire.service;

import java.util.List;
import com.smarhire.entity.JobApplication;

public interface JobApplicationService {

    void applyJob(Long jobId, Long candidateId);

    // 🔴 THIS METHOD MUST EXIST
    List<JobApplication> getMyApplications(Long candidateId);
}
