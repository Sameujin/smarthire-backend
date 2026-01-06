package com.smarhire.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(
    name = "job_applications",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"job_id", "candidate_id"})
    }
)
public class JobApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "job_id", nullable = false)
    private Long jobId;

    @Column(name = "candidate_id", nullable = false)
    private Long candidateId;

    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;

    private LocalDateTime appliedAt;

    @PrePersist
    public void onCreate() {
        this.appliedAt = LocalDateTime.now();
        this.status = ApplicationStatus.APPLIED;
    }
}
