package com.csb.dto;

import com.csb.enums.JobCategory;

public record CategoryChartDto(
        JobCategory category,
        long applications
) {
}
