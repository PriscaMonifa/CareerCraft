package com.evaluation.service;

import com.evaluation.dto.EmployerRegisterDto;
import com.evaluation.enums.Role;

import com.evaluation.exceptions.ResourceNotFoundException;
import com.evaluation.mapper.EmployerMapper;
import com.evaluation.mapper.UserMapper;
import com.evaluation.model.Employer;
import com.evaluation.model.User;
import com.evaluation.repository.EmployerRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmployerService {


    private final EmployerRepository employerRepository;
    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    public Employer getById(int employerId){
        return employerRepository.findById(employerId).orElseThrow(() -> new ResourceNotFoundException("Invalid Employer Id"));
    }

    public void postEmployer(@Valid EmployerRegisterDto employerRegisterDto) {
        Employer employer = EmployerMapper.mapDtoToEntity(employerRegisterDto);
        User user = UserMapper.mapDtoToEntity(employerRegisterDto);
        user.setRole(Role.EMPLOYER);
        user.setPassword(passwordEncoder.encode(employerRegisterDto.password()));
        user = userService.save(user);
        employer.setUser(user);
        employerRepository.save(employer);
    }
}
