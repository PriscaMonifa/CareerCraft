package com.evaluation.dto;

import java.util.List;

public record JobRespDto(
        long totalRecords,
        int totalPages,
        List<JobDto> data
) {
}
