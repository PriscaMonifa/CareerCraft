package com.csb.dto;

import com.csb.enums.JobCategory;
import com.csb.enums.JobType;

import java.time.Instant;

public record JobDto(
        int jobId,
        String jobTitle,
        String jobDescription,
        Double sal,
        String skills,
        String companyLocation,
        JobType jobType,
        JobCategory category,
        String companyName,
        Instant createdAt,
        Instant updatedAt,
        Boolean active
) {
}
