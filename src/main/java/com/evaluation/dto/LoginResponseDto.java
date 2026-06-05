package com.evaluation.dto;

public record LoginResponseDto(
        int id,
        String username,
        String role
) {
}