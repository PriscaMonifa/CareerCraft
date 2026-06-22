package com.csb.dto;

import com.csb.enums.ApplicationStatus;

public record ApplicationRecentDto(
        int applicationId,
        String jobTitle,
        String applicantName,
        String resumeUrl,
        ApplicationStatus status
) {
}
