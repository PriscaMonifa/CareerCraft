package com.csb.dto;

import com.csb.enums.ApplicationStatus;

public record ApplicationDto(
        int applicationId,
        String jobTitle,
        String applicantName,
        String resumeUrl,
        ApplicationStatus status
) {
}