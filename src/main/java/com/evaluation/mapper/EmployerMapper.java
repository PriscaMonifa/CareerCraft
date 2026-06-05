package com.evaluation.mapper;

import com.evaluation.dto.EmployerRegisterDto;
import com.evaluation.model.Employer;
import org.springframework.stereotype.Component;

@Component
public class EmployerMapper {
    public static Employer mapDtoToEntity(EmployerRegisterDto dto) {
        Employer employer = new Employer();
        employer.setCompanyName(dto.companyName());
        return employer;
    }
}
