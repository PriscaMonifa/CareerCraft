package com.evaluation.service;

import com.evaluation.dto.ApplicationSeekerDto;
import com.evaluation.mapper.ApplicationSeekerMapper;
import com.evaluation.model.Application;
import com.evaluation.model.Job;
import com.evaluation.model.JobSeeker;
import com.evaluation.repository.ApplicationRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class ApplicationService {

    private final JobService jobService;
    private final JobSeekerService jobSeekerService;
    private final ApplicationRepository applicationRepository;
    private final ApplicationSeekerMapper applicationSeekerMapper;

    //---APPLY JOB - JOBSEEKER ------
    public void apply(int jobId, int id) {
        Job job = jobService.getById(jobId);
        JobSeeker jobSeeker = jobSeekerService.getById(id);
        Application application = new Application();
        application.setJob(job);
        application.setJobSeeker(jobSeeker);
        application.setAppliedDate(LocalDate.now());
        applicationRepository.save(application);
    }

    //-----VIEW APPLICATION-JOBSEEKER------
    public List<ApplicationSeekerDto> getApplicationsByJobSeeker(int seekerId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<Application> applications = applicationRepository.findByJobSeekerId(seekerId, pageable).getContent();
        return applications.stream()
                .map(applicationSeekerMapper::mapEntityToDto)
                .toList();
    }
}
