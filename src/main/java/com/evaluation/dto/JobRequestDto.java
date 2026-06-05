package com.evaluation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record JobRequestDto(
        @NotBlank
        String title,
        @NotBlank
        String description,
        String location,
        @NotNull
        double salary
) {
}
