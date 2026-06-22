package com.csb.dto;

import com.csb.model.Job;

import java.util.List;

public record JobRespDto(long totalRecords,
                         int totalPages,
                         List<JobDto> data) {
}
