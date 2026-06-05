package com.evaluation.dto;

import java.time.LocalDate;

public record ApplicationSeekerDto(
        int applicationId,
        String title,
        String companyName,
        double salary,
        LocalDate appliedDate

) {
}
