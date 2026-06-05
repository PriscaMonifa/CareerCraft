package com.evaluation.mapper;

import com.evaluation.dto.JobSeekerRegisterDto;
import com.evaluation.model.JobSeeker;
import org.springframework.stereotype.Component;

@Component
public class JobSeekerMapper {

    public static JobSeeker mapDtoToEntity(JobSeekerRegisterDto dto) {
        JobSeeker jobSeeker = new JobSeeker();
        jobSeeker.setResumeSummary(dto.resumeSummary());
        return jobSeeker;
    }
}
