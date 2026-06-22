package com.csb.mapper;

import com.csb.dto.ApplicationDto;
import com.csb.model.Application;
import org.springframework.stereotype.Component;

@Component
public class ApplicationMapper {

    public ApplicationDto mapEntityToDto(Application application) {

        return new ApplicationDto(
                application.getId(),
                application.getJob().getJobTitle(),
                application.getJobSeeker().getUser().getName(),
                application.getJobSeeker().getResumeUrl(),
                application.getStatus()
        );
    }
    
}


