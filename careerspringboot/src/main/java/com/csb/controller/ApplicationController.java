package com.csb.controller;

import com.csb.dto.*;
import com.csb.enums.ApplicationStatus;
import com.csb.repository.ApplicationRepository;
import com.csb.service.ApplicationService;
import com.csb.service.JobSeekerService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/application")
@CrossOrigin(origins = "http://localhost:5173")

public class ApplicationController {

    private final ApplicationService applicationService;
    private final JobSeekerService jobSeekerService;
    private final ApplicationRepository applicationRepository;

                  //----APPLY FOR JOB - JOB-SEEKER -----
    @PostMapping("/apply/{jobId}")
    public void apply(@PathVariable int jobId, Principal principal) {
        String username= principal.getName();
        applicationService.apply(jobId,username);
    }

                 //-----VIEW JOBSEEKERS BY JOB - EMPLOYER-----
    @GetMapping("/job/{jobId}")
    public List<ApplicationDto> getApplicantsByJob(@PathVariable int jobId,
                                                   @RequestParam(defaultValue = "0") int page,
                                                   @RequestParam(defaultValue = "10") int size) {
        return applicationService.getApplicantsByJob(jobId, page, size);
    }
                    //----VIEW MY APPLICATION - JOBSEEKER------

    @GetMapping("/job-seeker")
    public Page<ApplicationSeekerDto> getApplicationsByJobSeeker(
            Principal principal,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) ApplicationStatus status) {
        String username = principal.getName();
        return applicationService.getApplicationsByJobSeeker(username,page, size,status);
    }
                    //UPDATE STATUS OF APPLICATION - EMPLOYER
    @PutMapping("/status/{applicationId}")
    public void updateStatus(@PathVariable int applicationId, @RequestParam ApplicationStatus status) {
        applicationService.updateStatus(applicationId, status);
    }
           //CHART FOR JOBSEEKER
    @GetMapping("/status-chart")
    public ApplicationStatusChartDto getApplicationStatusChart(
            Principal principal) {
        return applicationService
                .getApplicationStatusChart(principal.getName());
    }

          //RECENT APPLICATIONS-EMPLOYER
    @GetMapping("/recent/{employerId}")
    public Page<ApplicationRecentDto> getRecentApplications(
            @PathVariable int employerId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        return applicationService.getRecentApplications(employerId, page, size);
    }

    //CHART FOR EMPLOYER
    @GetMapping("/chart/category/{employerId}")
    public List<CategoryChartDto> getApplicationsByCategory(
            @PathVariable int employerId) {
        return applicationService.getApplicationsByCategory(employerId);
    }
}
