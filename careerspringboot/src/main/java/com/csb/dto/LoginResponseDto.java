package com.csb.dto;

public record LoginResponseDto(
        int id,
        String name,
        String username,
        String role
) {
}
