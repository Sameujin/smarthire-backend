package com.smarhire.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.smarhire.model.Job;

public interface JobRepository extends JpaRepository<Job, Long> {
}
