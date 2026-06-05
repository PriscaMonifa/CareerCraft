package com.evaluation.mapper;

import com.evaluation.dto.JobDto;
import com.evaluation.dto.JobRespDto;
import com.evaluation.model.Job;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JobMapper {

    public Job mapDtoToEntity(JobDto dto){
        Job job=new Job();
        job.setTitle(dto.title());
        job.setDescription(dto.description());
        job.setLocation(dto.location());
        job.setSalary(dto.salary());
        return job;
    }
    public JobRespDto mapEntityToDto(Page<Job> pages) {
        long totalElements = pages.getTotalElements();
        int totalPages = pages.getTotalPages();
        List<JobDto> list = pages.getContent()
                .stream()
                .map(job -> new JobDto(
                        job.getTitle(),
                        job.getDescription(),
                        job.getLocation(),
                        job.getSalary()

                
                        ))
                .toList();

        return new JobRespDto(totalElements, totalPages, list);
    }
}
