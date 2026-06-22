package com.csb.mapper;

import com.csb.dto.JobDto;
import com.csb.dto.JobRespDto;
import com.csb.model.Employer;
import com.csb.model.Job;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JobMapper {

    public static Job mapDtoToEntity(JobDto dto, Employer existingEmployer) {
        Job job = new Job();
        job.setJobTitle(dto.jobTitle());
        job.setJobDescription(dto.jobDescription());
        job.setSal(dto.sal());
        job.setSkills(dto.skills());
        existingEmployer.setCompanyLocation(dto.companyLocation());
        existingEmployer.setCompanyName(dto.companyName());
        job.setEmployer(existingEmployer);
        job.setJobType(dto.jobType());
        job.setCategory(dto.category());
        job.setCreatedAt(dto.createdAt());
        return job;
    }

    public static JobDto mapEntityToDto(Job job){
        return new JobDto(
                job.getJobId(),
                job.getJobTitle(),
                job.getJobDescription(),
                job.getSal(),
                job.getSkills(),
                job.getEmployer().getCompanyLocation(),
                job.getJobType(),
                job.getCategory(),
                job.getEmployer().getCompanyName(),
                job.getUpdatedAt(),
                job.getCreatedAt(),
                job.getActive()
        );
    }



}




