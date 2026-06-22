package com.csb.mapper;

import com.csb.dto.JobSeekerRegisterDto;
import com.csb.dto.JobSeekerRespDto;
import com.csb.model.JobSeeker;
import org.springframework.stereotype.Component;

@Component

public class JobSeekerMapper {

    public JobSeekerRespDto mapEntityToDto(JobSeeker jobSeeker){
        return new JobSeekerRespDto(
                jobSeeker.getSeekerId(),
                jobSeeker.getUser().getName(),
                jobSeeker.getUser().getEmail(),
                jobSeeker.getQualification(),
                jobSeeker.getEducation(),
                jobSeeker.getPreferredCategory(),
                jobSeeker.getContact(),
                jobSeeker.getSkills(),
                jobSeeker.getExperience(),
                jobSeeker.getLocation(),
                jobSeeker.getSocialUrl(),
                jobSeeker.getResumeUrl()
        );
    }
    public static JobSeeker mapDtoToEntity(JobSeekerRegisterDto dto) {
        JobSeeker jobSeeker = new JobSeeker();
        return jobSeeker;
    }
}
