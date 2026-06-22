package com.csb.dto;

import com.csb.enums.ApplicationStatus;

public record ApplicationSeekerDto(
        int applicationId,
        String jobTitle,
        String companyName,
        String location,
        double salary,
        ApplicationStatus status
) {
}
