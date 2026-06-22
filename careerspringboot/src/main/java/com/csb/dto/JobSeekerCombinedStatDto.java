package com.csb.dto;

import java.util.List;

public record JobSeekerCombinedStatDto(
        List<String> labels,
        List<Long> counts
) {
}
