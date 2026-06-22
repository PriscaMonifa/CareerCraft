package com.csb.service;

import com.csb.dto.*;
import com.csb.enums.ApplicationStatus;
import com.csb.enums.Role;
import com.csb.exception.ResourceNotFoundException;
import com.csb.mapper.JobSeekerMapper;
import com.csb.mapper.UserMapper;
import com.csb.model.Application;
import com.csb.model.Job;
import com.csb.model.JobSeeker;
import com.csb.model.User;
import com.csb.repository.ApplicationRepository;
import com.csb.repository.JobRepository;
import com.csb.repository.JobSeekerRepository;
import com.csb.repository.UserRepository;
import com.csb.utility.FileUtility;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor

public class JobSeekerService {

    private final JobSeekerRepository jobSeekerRepository;
    private final JobSeekerMapper jobSeekerMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final UserRepository userRepository;
    private final ApplicationRepository applicationRepository;
    private final JobRepository jobRepository;

    private static final String UPLOAD_LOC = "C:/Users/Lenovo/OneDrive/Desktop/Documents/Study Materials/Uploads";

                    //----REGISTRATION-----
    public void postJobSeeker(@Valid JobSeekerRegisterDto jobSeekerRegisterDto) {
        JobSeeker jobSeeker = JobSeekerMapper.mapDtoToEntity(jobSeekerRegisterDto);
        User user = UserMapper.mapDtoToEntity(jobSeekerRegisterDto);
        user.setRole(Role.JOB_SEEKER);
        user.setPassword(passwordEncoder.encode(jobSeekerRegisterDto.password()));
        user = userService.save(user);
        jobSeeker.setUser(user);
        jobSeekerRepository.save(jobSeeker);
    }

    public JobSeeker getById(int seekerId) {
        return jobSeekerRepository.findById(seekerId).orElseThrow(() -> new ResourceNotFoundException("Invalid JobSeeker Id"));
    }

                   //---PROFILE-----
    public JobSeekerRespDto getJobSeekerById(int seekerId) {
        JobSeeker jobSeeker = getById(seekerId);
        return jobSeekerMapper.mapEntityToDto(jobSeeker);
    }

           //----------DELETE ACCOUNT-------
    public void deleteById(int seekerId) {
        getById(seekerId);
        jobSeekerRepository.deleteById(seekerId);
    }

               //------UPDATE PROFILE-------
    public void update(int seekerId, JobSeekerDto dto) {
        JobSeeker existingJobSeeker = getById(seekerId);
        existingJobSeeker.setQualification(dto.qualification());
        existingJobSeeker.setEducation(dto.education());
        existingJobSeeker.setSkills(dto.skills());
        existingJobSeeker.setExperience(dto.experience());
        existingJobSeeker.setLocation(dto.location());
        existingJobSeeker.setPreferredCategory(dto.preferredCategory());
        existingJobSeeker.setContact(dto.contact());
        existingJobSeeker.setSocialUrl(dto.socialUrl());
        //System.out.println("Updating seekerId = " + seekerId);
        //System.out.println("DTO = " + dto);
        jobSeekerRepository.save(existingJobSeeker);
    }

               //----RESUME UPLOAD-----
    public void upload(String username, MultipartFile file) throws IOException {
        JobSeeker jobSeeker = getByUsername(username);
        FileUtility.validateFile(file);
        String originalFileName = file.getOriginalFilename();
        String ext = originalFileName.substring(
                originalFileName.lastIndexOf(".")
        ).toLowerCase();
        String fileName = UUID.randomUUID() + ext;
        Path uploadPath = Paths.get(UPLOAD_LOC);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        Path destinationPath = uploadPath.resolve(fileName);
        Files.copy(file.getInputStream(), destinationPath, StandardCopyOption.REPLACE_EXISTING);
        jobSeeker.setResumeUrl(fileName);
        jobSeekerRepository.save(jobSeeker);
    }

    private JobSeeker getByUsername(String username) {
        return jobSeekerRepository.findByUserUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("JobSeeker not found for user: " + username));
    }


    public JobSeekerRespDto getProfile(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));
        JobSeeker jobSeeker = jobSeekerRepository
                .findByUser(user)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Profile not found"));

        return jobSeekerMapper.mapEntityToDto(jobSeeker);
    }

    public JobSeeker findByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return jobSeekerRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("JobSeeker not found"));
    }

    public JobSeekerCombinedStatDto getCombinedStats(String username) {
        JobSeeker seeker = jobSeekerRepository
                .findByUserUsername(username)
                .orElseThrow(() -> new RuntimeException("Job Seeker not found"));
        List<Application> totalApplications =
                applicationRepository.findAllByJobSeekerUserUsername(username);
        List<Application> awaitingResponse =
                applicationRepository.findAllByJobSeekerUserUsernameAndStatus(
                        username, ApplicationStatus.APPLIED);
        long companiesApplied = totalApplications.stream()
                .map(app -> app.getJob().getEmployer().getEmployerId())
                .distinct()
                .count();
        Pageable pageable = PageRequest.of(0, 100); // adjust size as needed
        Page<Job> jobsNearMe = jobRepository.findAllByActiveTrue(pageable);
        List<String> labels = List.of(
                "Total Applications",
                "Awaiting Response",
                "Companies Applied",
                "Jobs Near Me"
        );
        List<Long> counts = List.of(
                (long) totalApplications.size(),
                (long) awaitingResponse.size(),
                companiesApplied,
                 jobsNearMe.getTotalElements()
        );
        return new JobSeekerCombinedStatDto(labels, counts);
    }

}

