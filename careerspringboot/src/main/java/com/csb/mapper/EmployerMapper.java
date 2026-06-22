package com.csb.mapper;

import com.csb.dto.EmployerDto;
import com.csb.dto.EmployerRegisterDto;
import com.csb.dto.EmployerRespDto;
import com.csb.model.Employer;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component

public class EmployerMapper {
    public static EmployerRespDto mapEntityToDto(Employer employer){
        return new EmployerRespDto(
                employer.getEmployerId(),
                employer.getCompanyName(),
                employer.getCompanyLocation(),
                employer.getUser().getName(),
                employer.getUser().getEmail(),
                employer.getContactNumber()
        );
    }
    //Register dto
    public static Employer mapDtoToEntity(EmployerRegisterDto dto) {
        Employer employer = new Employer();
        employer.setCompanyName(dto.companyName());
        employer.setCompanyLocation(dto.companyLocation());
        employer.setContactNumber(dto.contactNumber());
        return employer;
    }

}
