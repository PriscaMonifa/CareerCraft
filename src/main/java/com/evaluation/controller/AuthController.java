package com.evaluation.controller;

import com.evaluation.dto.EmployerRegisterDto;
import com.evaluation.dto.JobSeekerRegisterDto;
import com.evaluation.dto.LoginResponseDto;
import com.evaluation.dto.TokenDto;
import com.evaluation.model.User;
import com.evaluation.service.EmployerService;
import com.evaluation.service.JobSeekerService;
import com.evaluation.service.UserService;
import com.evaluation.utility.JwtUtility;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    public final UserService userService;
    public final JobSeekerService jobSeekerService;
    public final EmployerService employerService;
    public final JwtUtility jwtUtility;

         //-----LOGIN-----
    @GetMapping("/login")
    public TokenDto login(Principal principal) {
        String username = principal.getName();
        String token = jwtUtility.generateToken(username);
        return new TokenDto(username, token);
    }

    @GetMapping("/user-details")
    public LoginResponseDto getUserDetails(Principal principal) {
        User user = (User) userService.loadUserByUsername(principal.getName());
        return new LoginResponseDto(
                user.getId(),
                user.getUsername(),
                user.getRole().toString()
        );
    }

         //------REGISTRATION FRO JOBSEEKER AND EMPLOYER --------
    @PostMapping("/jobSeeker/register")
    public void postJobSeeker(@Valid @RequestBody JobSeekerRegisterDto jobSeekerRegisterDto){
        jobSeekerService.postJobSeeker(jobSeekerRegisterDto);
    }

    @PostMapping("/employer/register")
    public void postEmployer(@Valid @RequestBody EmployerRegisterDto employerRegisterDto) {
        employerService.postEmployer(employerRegisterDto);
    }
}
