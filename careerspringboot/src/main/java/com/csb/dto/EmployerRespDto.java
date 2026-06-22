package com.csb.dto;

public record EmployerRespDto(
        int employerId,
        String companyName,
        String companyLocation,
        String employerName,
        String email,
        String contactNumber
) {
}
