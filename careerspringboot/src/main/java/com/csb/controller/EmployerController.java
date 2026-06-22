package com.csb.controller;

import com.csb.dto.EmployerCombinedStatDto;
import com.csb.dto.EmployerDto;
import com.csb.dto.EmployerRegisterDto;
import com.csb.dto.EmployerRespDto;
import com.csb.service.EmployerService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@AllArgsConstructor
@RequestMapping("/api/employer")
@CrossOrigin(origins = "http://localhost:5173")
public class EmployerController {
    private final EmployerService employerService;

                        // ---------REGISTRATION--------
    @PostMapping("/register")
    public void postEmployer(@Valid @RequestBody EmployerRegisterDto employerRegisterDto) {
        employerService.postEmployer(employerRegisterDto);
    }

                       // -------PROFILE UPDATE--------
    @PutMapping("/update/{employerId}")
    public void update(@PathVariable int employerId, @RequestBody EmployerDto dto) {
        employerService.update(employerId, dto);
    }
                          //----PROFILE-----
    @GetMapping("/{employerId}")
    public ResponseEntity<EmployerRespDto> getById(@PathVariable int employerId) {
        return ResponseEntity.ok(employerService.getEmployerById(employerId));
    }
                          //----DELETE ACCOUNT-----
    @DeleteMapping("/delete/{employerId}")
    public void delete(@PathVariable int employerId) {
        employerService.delete(employerId);
    }

    @GetMapping("/employer-stats")
    public EmployerCombinedStatDto getCombinedStats(Principal principal){
        String employerUsername= principal.getName();
        return employerService.getCombinedStats(employerUsername);
    }
}
