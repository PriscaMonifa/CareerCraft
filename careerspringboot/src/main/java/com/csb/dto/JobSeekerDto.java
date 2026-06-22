package com.csb.dto;

import com.csb.enums.JobCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record JobSeekerDto(
        @NotBlank(message = "Qualification is mandatory")
        String qualification,

        @NotBlank(message = "Education  is mandatory")
        String education,

        @NotBlank(message = "Skills are mandatory")
        String skills,

        @NotNull(message = "Experience is mandatory")
        Integer experience,

        @NotBlank(message = "Location is mandatory")
        String location,

        JobCategory preferredCategory,

        String contact,

        String socialUrl




) {
}
