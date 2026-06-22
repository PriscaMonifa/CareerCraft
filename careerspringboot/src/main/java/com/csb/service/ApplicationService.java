package com.csb.service;

import com.csb.dto.*;
import com.csb.enums.ApplicationStatus;
import com.csb.exception.ResourceNotFoundException;
import com.csb.mapper.ApplicationMapper;
import com.csb.mapper.ApplicationSeekerMapper;
import com.csb.model.Application;
import com.csb.model.Job;
import com.csb.model.JobSeeker;
import com.csb.model.User;
import com.csb.repository.ApplicationRepository;
import com.csb.repository.JobSeekerRepository;
import com.csb.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import static com.csb.enums.ApplicationStatus.APPLIED;

@Service
@AllArgsConstructor

public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final ApplicationMapper applicationMapper;
    private final ApplicationSeekerMapper applicationSeekerMapper;
    private final JobService jobService;
    private final JobSeekerService jobSeekerService;
    private final UserRepository userRepository;
    private final JobSeekerRepository jobSeekerRepository;
    private final NotificationService notificationService;

                     //---APPLY JOB - JOBSEEKER ------

    public void apply(int jobId, String username) {
        Job job = jobService.getById(jobId);

        User user = userRepository.findByUsername(username)
                .orElseThrow();

        JobSeeker jobSeeker = jobSeekerRepository.findByUser(user)
                .orElseThrow();

        Application application = new Application();
        application.setJob(job);
        application.setJobSeeker(jobSeeker);
        application.setStatus(APPLIED);
        application.setAppliedDate(LocalDate.now());
        applicationRepository.save(application);
        notificationService.createNotification(job.getEmployer().getUser(),
                "New Application", jobSeeker.getUser().getName()
                        + " applied for " + job.getJobTitle(),
                NotificationType.NEW_APPLICATION
        );
    }

                    //----VIEW JOBSEEKERS FOR A JOB - EMPLOYER ------
    public List<ApplicationDto> getApplicantsByJob(int jobId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<Application> applications = applicationRepository.findByJobJobId(jobId, pageable).getContent();
        return applications.stream()
                .map(applicationMapper::mapEntityToDto)
                .toList();
    }

                    //-----VIEW APPLICATION-JOBSEEKER------

    public Page<ApplicationSeekerDto> getApplicationsByJobSeeker(String username, int page, int size,ApplicationStatus status) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("appliedDate").descending()  );
        Page<Application> applications;
        if (status != null) {
            applications = applicationRepository
                    .findByJobSeekerUserUsernameAndStatus(username, status, pageable);
        } else {
            applications = applicationRepository
                    .findByJobSeekerUserUsername(username, pageable);
        }
        return applications.map(applicationSeekerMapper::mapEntityToDto);
    }

    public Application getById(int applicationId) {
        return applicationRepository.findById(applicationId)
                .orElseThrow(() -> new ResourceNotFoundException("Invalid Application Id"));
    }

                        // UPDATE STATUS OF AN APPLICATION------
    public void updateStatus(int applicationId, ApplicationStatus status) {
        Application application = getById(applicationId);
        application.setStatus(status);
        applicationRepository.save(application);
        notificationService.createNotification(
                application.getJobSeeker().getUser(), "Application Status Updated", "Your application for "
                        + application.getJob().getJobTitle() + " is now " + status,NotificationType.APPLICATION_STATUS
        );
    }

    public ApplicationStatusChartDto getApplicationStatusChart(String username){
        long applied = applicationRepository
                .countByJobSeekerUserUsernameAndStatus(username, ApplicationStatus.APPLIED);
        long shortlisted = applicationRepository
                .countByJobSeekerUserUsernameAndStatus(username, ApplicationStatus.SHORTLISTED);
        long rejected = applicationRepository
                .countByJobSeekerUserUsernameAndStatus(username, ApplicationStatus.REJECTED);
        long hired = applicationRepository
                .countByJobSeekerUserUsernameAndStatus(username, ApplicationStatus.HIRED);
        return new ApplicationStatusChartDto(
                List.of("Applied","Shortlisted","Rejected","Hired"),
                List.of(applied, shortlisted, rejected, hired)
        );
    }


    public Page<ApplicationRecentDto> getRecentApplications(int employerId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Application> applications =
                applicationRepository.findRecentApplications(employerId, pageable);
        return applications.map(app ->
                new ApplicationRecentDto(
                        app.getId(),
                        app.getJob().getJobTitle(),
                        app.getJobSeeker().getUser().getName(),
                        app.getJobSeeker().getResumeUrl(),
                        app.getStatus()
                )
        );
    }

    public List<CategoryChartDto> getApplicationsByCategory(int employerId) {
        return applicationRepository.getApplicationsByCategory(employerId);
    }
}