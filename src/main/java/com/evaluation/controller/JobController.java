package com.evaluation.controller;

import com.evaluation.dto.JobDto;
import com.evaluation.dto.JobRespDto;
import com.evaluation.service.JobService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/job")
@AllArgsConstructor
public class JobController {

    private final JobService jobService;

    //---- POST A NEW JOB - EMPLOYER -------
    @PostMapping("/add/{employerId}")
    public void addJobWithEmployer(@Valid @RequestBody JobDto jobDto, @PathVariable int employerId){
        jobService.addJobWithEmployer(jobDto,employerId);
    }

    //-----BROWSE ALL JOBS------
    @GetMapping("/all")
    public JobRespDto getAll(@RequestParam(defaultValue = "0", required = false) int page,
                             @RequestParam(defaultValue = "10", required = false) int size) {
        return jobService.getAllWithPagination(page, size);
    }


}
