package com.evaluation.mapper;

import com.evaluation.dto.ApplicationSeekerDto;
import com.evaluation.model.Application;
import org.springframework.stereotype.Component;

@Component
public class ApplicationSeekerMapper {
    public ApplicationSeekerDto mapEntityToDto(Application application) {
        return new ApplicationSeekerDto(
                application.getId(),
                application.getJob().getTitle(),
                application.getJob().getEmployer().getCompanyName(),
                application.getJob().getSalary(),
                application.getAppliedDate()
        );
    }
}
