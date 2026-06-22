package com.csb.model;

import com.csb.enums.JobCategory;
import com.csb.enums.JobType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Entity
@Getter
@Setter
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int jobId;

    @Column(nullable = false)
    private String jobTitle;

    private String jobDescription;

    private double sal;

    private String skills;

    @Enumerated(EnumType.STRING)
    private JobType jobType;

    @Enumerated(EnumType.STRING)
    private JobCategory category;

    @CreationTimestamp
    @Column(updatable = false)
    private Instant createdAt;

    @Column(name = "is_active")
    private Boolean active;

    @UpdateTimestamp
    private Instant updatedAt;

    @ManyToOne
    private Employer employer;

}
