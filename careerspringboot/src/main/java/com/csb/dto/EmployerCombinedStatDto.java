package com.csb.dto;

import java.util.List;

public record EmployerCombinedStatDto(
        List<String> label,
        List<Long> count
) {
}
