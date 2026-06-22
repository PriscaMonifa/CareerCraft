package com.csb.service;

import com.csb.dto.JobDto;
import com.csb.dto.JobRespDto;
import com.csb.enums.JobCategory;
import com.csb.enums.JobType;
import com.csb.exception.ResourceNotFoundException;
import com.csb.mapper.JobMapper;
import com.csb.model.Employer;
import com.csb.model.Job;

import com.csb.model.JobSeeker;
import com.csb.model.User;
import com.csb.repository.EmployerRepository;
import com.csb.repository.JobRepository;

import com.csb.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Service
@AllArgsConstructor
public class JobService {
    private final JobRepository jobRepository;
    private final JobMapper jobMapper;
    private final EmployerService employerService;
    private final JobSeekerService jobSeekerService;
    private final UserRepository userRepository;
    private final EmployerRepository employerRepository;

                           //----POST A JOB------
    public void addJobWithEmployer(JobDto jobDto, int employerId) {
        Employer employer=employerService.getById(employerId);
        Job job=JobMapper.mapDtoToEntity(jobDto,employer);
        jobRepository.save(job);

    }
    public Job getById(int jobId){
        return jobRepository.findById(jobId).orElseThrow(()->new ResourceNotFoundException("Invalid id.."));
    }

                          //----GET A JOB BY ID--------
    public JobDto getJobById(int jobId){
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new ResourceNotFoundException("Invalid id.."));
        return JobMapper.mapEntityToDto(job);
    }

                           //-----DELETE A JOB------
    public void deleteById(int jobId){
        getById(jobId);
        jobRepository.deleteById(jobId);

    }
                             //-----UPDATE JOB DETAILS
    public void update(int jobId, Job updatedJob) {
        Job exisitngJob = getById(jobId);
        exisitngJob.setJobTitle(updatedJob.getJobTitle());
        exisitngJob.setJobDescription(updatedJob.getJobDescription());
        exisitngJob.setSal(updatedJob.getSal());
        exisitngJob.setSkills(updatedJob.getSkills());
        jobRepository.save(exisitngJob);
    }

                            //-----LIST OF ALL JOBS-----
    public Page<JobDto> getAllWithPagination(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Job> pages = jobRepository.findAllActive(true,pageable);
        return pages.map(JobMapper::mapEntityToDto);
    }

                            //-----GET A JOB BY JOB-TITLE------
    public List<JobDto> getByJobTitle(String jobTitle, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<Job> jobs = jobRepository.findAllByJobTitle(jobTitle, pageable).getContent();
        return jobs.stream()
                .map(JobMapper::mapEntityToDto)
                .toList();
    }

                          //----MULTI FIELD SEARCH-----
    public List<JobDto> searchJobs(String jobTitle, String companyLocation,
            int page, int size) {

        Pageable pageable = PageRequest.of(page, size);
        List<Job> jobs = jobRepository
                .findByJobTitleContainingIgnoreCaseAndEmployer_CompanyLocationContainingIgnoreCase(
                        jobTitle, companyLocation, pageable).getContent();
        return jobs.stream()
                .map(JobMapper::mapEntityToDto)
                .toList();
    }

                            //-----RECOMMENDED JOBS-----

    public List<JobDto> getRecommendedJobs(int seekerId, int page, int size) {
        JobSeeker jobSeeker = jobSeekerService.getById(seekerId);
        Pageable pageable = PageRequest.of(page, size);
        return jobRepository
                .findByCategoryAndActiveTrue(jobSeeker.getPreferredCategory(), pageable)
                .getContent()
                .stream()
                .map(JobMapper::mapEntityToDto)
                .toList();
    }

    public Page<JobDto> getJobsPostedByEmployer(String username, int page, int size) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
        Employer employer = employerRepository.findByUser(user).orElseThrow(() -> new RuntimeException("Employer not found"));
        Page<Job> jobs = jobRepository.findByEmployer(
                employer,
                PageRequest.of(page, size)
        );
        return jobs.map(JobMapper::mapEntityToDto);
    }

    public void toggleJobActive(int jobId) {
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));
        job.setActive(job.getActive() == null || !job.getActive());
        jobRepository.save(job);
    }
}
