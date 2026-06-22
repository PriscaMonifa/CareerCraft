package com.csb.mapper;

import com.csb.dto.ApplicationSeekerDto;
import com.csb.model.Application;
import org.springframework.stereotype.Component;

@Component
public class ApplicationSeekerMapper {
    public ApplicationSeekerDto mapEntityToDto(Application application) {
        return new ApplicationSeekerDto(
                application.getId(),
                application.getJob().getJobTitle(),
                application.getJob().getEmployer().getCompanyName(),
                application.getJob().getEmployer().getCompanyLocation(),
                application.getJob().getSal(),
                application.getStatus()
        );
    }
}
