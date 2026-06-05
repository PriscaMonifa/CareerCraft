package com.evaluation.service;

import com.evaluation.dto.JobSeekerRegisterDto;
import com.evaluation.enums.Role;
import com.evaluation.exceptions.ResourceNotFoundException;
import com.evaluation.mapper.JobSeekerMapper;
import com.evaluation.mapper.UserMapper;
import com.evaluation.model.JobSeeker;
import com.evaluation.model.User;
import com.evaluation.repository.JobSeekerRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class JobSeekerService {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final JobSeekerRepository jobSeekerRepository;

    //----REGISTRATION-----
    public void postJobSeeker(@Valid JobSeekerRegisterDto jobSeekerRegisterDto) {
        JobSeeker jobSeeker = JobSeekerMapper.mapDtoToEntity(jobSeekerRegisterDto);
        jobSeeker.setResumeSummary(jobSeekerRegisterDto.resumeSummary());
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
}
