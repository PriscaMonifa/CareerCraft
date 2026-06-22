package com.csb.controller;


import com.csb.dto.JobDto;
import com.csb.dto.JobRespDto;
import com.csb.enums.JobCategory;
import com.csb.enums.JobType;
import com.csb.model.Job;
import com.csb.service.EmployerService;
import com.csb.service.JobSeekerService;
import com.csb.service.JobService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/jobs")
@CrossOrigin(origins = "http://localhost:5173")
public class JobController {
    private final JobService jobService;
    private final JobSeekerService jobSeekerService;
    private final EmployerService employerService;

                     //-----LIST OF ALL JOBS------
    @GetMapping("/all")
    public Page<JobDto> getAll(@RequestParam(defaultValue = "0", required = false) int page,
                             @RequestParam(defaultValue = "10", required = false) int size) {
        return jobService.getAllWithPagination(page, size);
    }

                   //-----JOBS POSTED BY PARTICULAR EMPLOYER
    @GetMapping("/employerJobs")
    public Page<JobDto> getMyJobs(Principal principal, @RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "6") int size) {
        String username = principal.getName();
        return jobService.getJobsPostedByEmployer(username, page, size);
    }

                       //---- POST A JOB-------
    @PostMapping("/add/{employerId}")
    public void addJobWithEmployer(@Valid @RequestBody JobDto jobDto, @PathVariable int employerId){
        jobService.addJobWithEmployer(jobDto,employerId);
    }

                       //---SEARCH JOB BY ID----
    @GetMapping("/get-one/{jobId}")
    public ResponseEntity<JobDto> getJobById(@PathVariable int jobId){
        return ResponseEntity.ok(jobService.getJobById(jobId));
    }

                         //----DELETE JOB-------
    @DeleteMapping("/delete/{jobId}")
    public ResponseEntity<String> deleteById(@PathVariable int jobId){
        jobService.deleteById(jobId);
        return ResponseEntity.ok("Job deleted successfully");
    }

                         //-----UPDATE JOB-------
    @PutMapping("/update/{jobId}")
    public void update(@PathVariable int jobId, @RequestBody Job updatedJob){
        jobService.update(jobId, updatedJob);
    }

                        //---SEARCH JOB BY TITLE------

    @GetMapping("/jobTitle")
    public List<JobDto> getByJobTitle(@RequestParam String jobTitle,
            @RequestParam(defaultValue = "0", required = false) int page,
            @RequestParam(defaultValue = "10", required = false) int size) {
        return jobService.getByJobTitle(jobTitle,page,size);
    }
                        //-----MULTI FIELD SEARCH------

    @GetMapping("/search")
    public List<JobDto> searchJobs(
            @RequestParam String jobTitle,
            @RequestParam String companyLocation,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return jobService.searchJobs(jobTitle, companyLocation, page, size);
    }

                           //-----RECOMMENDED JOBS-----

    @GetMapping("/recommend/{seekerId}")
    public List<JobDto> getRecommendedJobs(
            @PathVariable int seekerId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return jobService.getRecommendedJobs(seekerId, page, size);
    }
         //TOGGLE
    @PutMapping("/toggle-active/{jobId}")
    public void toggleJob(@PathVariable int jobId) {
        jobService.toggleJobActive(jobId);
    }
}
