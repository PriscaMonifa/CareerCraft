package com.evaluation.dto;

import java.time.LocalDate;

public record ApplicationResponseDto(
        LocalDate appliedAt,
        String title,
        String companyName

) {
}
