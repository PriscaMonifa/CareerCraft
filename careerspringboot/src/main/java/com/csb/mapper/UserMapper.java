package com.csb.mapper;

import com.csb.dto.EmployerRegisterDto;
import com.csb.dto.JobSeekerRegisterDto;
import com.csb.model.User;

public class UserMapper {
    public static User mapDtoToEntity(JobSeekerRegisterDto dto) {
        User user = new User();
        user.setName(dto.name());
        user.setUsername(dto.username());
        user.setEmail(dto.email());
        // Password and Role are set explicitly inside the service layer
        return user;
    }
    public static User mapDtoToEntity(EmployerRegisterDto dto) {
        User user = new User();
        user.setName(dto.name());
        user.setUsername(dto.username());
        user.setEmail(dto.email());
        return user;
    }
}
