package com.csb.service;

import com.csb.dto.EmployerCombinedStatDto;
import com.csb.dto.EmployerDto;
import com.csb.dto.EmployerRegisterDto;
import com.csb.dto.EmployerRespDto;
import com.csb.enums.ApplicationStatus;
import com.csb.enums.Role;
import com.csb.exception.ResourceNotFoundException;
import com.csb.mapper.EmployerMapper;
import com.csb.mapper.UserMapper;
import com.csb.model.Application;
import com.csb.model.Employer;
import com.csb.model.Job;
import com.csb.model.User;
import com.csb.repository.ApplicationRepository;
import com.csb.repository.EmployerRepository;
import com.csb.repository.JobRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class EmployerService {
    private final EmployerRepository employerRepository;
    private final EmployerMapper employerMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final JobRepository jobRepository;
    private final ApplicationRepository applicationRepository;

    @Value("${employer.password.temp}")
    private String employerTempPassword;

                    //-------- REGISTRATION---------
    public void postEmployer(@Valid EmployerRegisterDto employerRegisterDto) {
        Employer employer = EmployerMapper.mapDtoToEntity(employerRegisterDto);
        String password = employerTempPassword;
        User user = UserMapper.mapDtoToEntity(employerRegisterDto);
        user.setRole(Role.EMPLOYER);
        user.setPassword(passwordEncoder.encode(password));
        user = userService.save(user);
        employer.setUser(user);
        employerRepository.save(employer);
    }

    public Employer getById(int employerId){
        return employerRepository.findById(employerId).orElseThrow(() -> new ResourceNotFoundException("Invalid Employer Id"));
    }
                         //-----PROFILE----
    public EmployerRespDto getEmployerById(int employerId){
        Employer employer = getById(employerId);
        //System.out.println("Contact Number = " + employer.getContactNumber());
        return EmployerMapper.mapEntityToDto(employer);
    }
                        //-----DELETE ACCOUNT-----
    public void delete(int employerId){
        getById(employerId);
        employerRepository.deleteById(employerId);
    }

                        //------PROFILE UPDATE ------
    public void update(int employerId, EmployerDto dto) {
        Employer employer = getById(employerId);
        employer.setCompanyName(dto.companyName());
        employer.setCompanyLocation(dto.companyLocation());
        employer.setContactNumber(dto.contactNumber());
        employerRepository.save(employer);
    }

                         //------WIDGET-----
    public EmployerCombinedStatDto getCombinedStats(String employerUsername) {
        List<Job> totalJobs= jobRepository.findAllByEmployerUserUsername(employerUsername);
        List<Job> activeJobs = jobRepository.findAllByEmployerUserUsernameAndActive(employerUsername,true);
        List<Application> totalApplications = applicationRepository.findAllByJobEmployerUserUsername(employerUsername);
        List<Application> newApplications = applicationRepository.findAllByJobEmployerUserUsernameAndStatus(employerUsername, ApplicationStatus.APPLIED);

        List<String> labels = List.of("Total Jobs", "Active Jobs", "Total Applications", "New Applications");
        List<Long> counts = List.of((long)totalJobs.size(), (long)activeJobs.size(),(long) totalApplications.size(),(long) newApplications.size());

        return new EmployerCombinedStatDto(labels, counts);

    }
}
