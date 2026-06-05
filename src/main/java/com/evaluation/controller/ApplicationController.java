package com.evaluation.controller;

import com.evaluation.dto.ApplicationSeekerDto;
import com.evaluation.service.ApplicationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/application")
@AllArgsConstructor
public class ApplicationController {

    private final ApplicationService applicationService;


    //----APPLY FOR  A JOB - JOB-SEEKER -----
    @PostMapping("/apply/{jobId}/{seekerId}")
    public void apply(@PathVariable int jobId, @PathVariable int seekerId) {
        applicationService.apply(jobId, seekerId);
    }


    //----VIEW MY APPLICATION - JOBSEEKER------
    @GetMapping("/job-seeker/{seekerId}")
    public List<ApplicationSeekerDto> getApplicationsByJobSeeker(@PathVariable int seekerId,
                                                                 @RequestParam(defaultValue = "0") int page,
                                                                 @RequestParam(defaultValue = "10") int size) {
        return applicationService.getApplicationsByJobSeeker(seekerId, page, size);
    }
}
