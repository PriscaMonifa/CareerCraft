package com.csb.dto;

import java.util.List;

public record ApplicationStatusChartDto(
        List<String> labels,
        List<Long> counts
) {
}
