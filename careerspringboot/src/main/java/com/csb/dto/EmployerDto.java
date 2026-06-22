package com.csb.dto;

import jakarta.validation.constraints.NotBlank;

public record EmployerDto(
        @NotBlank(message = "Company name is mandatory")
        String companyName,

        @NotBlank(message = "Company Location is mandatory")
        String companyLocation,

        String contactNumber
) {
}
