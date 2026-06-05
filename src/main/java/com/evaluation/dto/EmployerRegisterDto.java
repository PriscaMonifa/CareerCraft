package com.evaluation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record EmployerRegisterDto(
        @NotNull
        @NotBlank
        String name,
        @NotNull
        @NotBlank
        @Size(min=4)
        String username,
        @NotNull
        @NotBlank
        String password,
        @NotNull
        @NotBlank
        String email,
        @NotNull
        @NotBlank
        String companyName,
        @NotNull
        @NotBlank
        String companyLocation
) {
}
