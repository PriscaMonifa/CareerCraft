package com.csb.controller;

import com.csb.dto.JobSeekerCombinedStatDto;
import com.csb.dto.JobSeekerDto;
import com.csb.dto.JobSeekerRegisterDto;
import com.csb.dto.JobSeekerRespDto;
import com.csb.service.JobSeekerService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/jobseeker")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")

public class JobSeekerController {
    private final JobSeekerService jobSeekerService;

                   //------REGISTRATION--------
    @PostMapping("/register")
    public void postJobSeeker(@Valid @RequestBody JobSeekerRegisterDto jobSeekerRegisterDto){
        jobSeekerService.postJobSeeker(jobSeekerRegisterDto);
    }
                     //----PROFILE--------
    @GetMapping("/{seekerId}")
    public ResponseEntity<JobSeekerRespDto> getById(@PathVariable int seekerId){
        return ResponseEntity.ok(jobSeekerService.getJobSeekerById(seekerId));
    }

    @GetMapping("/profile")
    public ResponseEntity<JobSeekerRespDto> getProfile(Principal principal) {
        String username = principal.getName();
        return ResponseEntity.ok(jobSeekerService.getProfile(username)
        );
    }
                    //-----DELETE ACCOUNT-----
    @DeleteMapping("/delete/{seekerId}")
    public void deleteById(@PathVariable int seekerId){
        jobSeekerService.deleteById(seekerId);
    }

                    //------PROFILE UPDATE--------
    @PutMapping("/update/{seekerId}")
    public void update(@PathVariable int seekerId, @RequestBody JobSeekerDto dto) {
        jobSeekerService.update(seekerId, dto);
    }
                        //-----RESUME UPLOAD----
    @PostMapping("/resume/upload")
    public ResponseEntity<String> upload(Principal principal, @RequestParam("file") MultipartFile file) throws IOException {
        String username = principal.getName();
        jobSeekerService.upload(username, file);
        return ResponseEntity.ok("Resume uploaded successfully");

    }

    @GetMapping("/jobseeker-stats")
    public JobSeekerCombinedStatDto getCombinedStats(Principal principal) {
        return jobSeekerService.getCombinedStats(principal.getName());
    }
}