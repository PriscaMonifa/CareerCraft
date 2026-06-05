package com.evaluation.service;

import com.evaluation.dto.JobDto;
import com.evaluation.dto.JobRespDto;
import com.evaluation.exceptions.ResourceNotFoundException;
import com.evaluation.mapper.JobMapper;
import com.evaluation.model.Employer;
import com.evaluation.model.Job;
import com.evaluation.repository.EmployerRepository;
import com.evaluation.repository.JobRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class JobService {

    private final EmployerService employerService;
    private final JobMapper jobMapper;
    private final JobRepository jobRepository;
    private final EmployerRepository employerRepository;

    //----POST A JOB------
    public void addJobWithEmployer(JobDto jobDto, int employerId) {
        Employer employer=employerService.getById(employerId);
        Job job=jobMapper.mapDtoToEntity(jobDto);
        job.setEmployer(employer);
        jobRepository.save(job);

    }

    //-----LIST OF ALL JOBS-----
    public JobRespDto getAllWithPagination(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Job> pages = jobRepository.findAll(pageable);
        return jobMapper.mapEntityToDto(pages);
    }

    public Job getById(int jobId){
        return jobRepository.findById(jobId).orElseThrow(()->new ResourceNotFoundException("Invalid id.."));
    }

}
