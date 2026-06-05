package com.evaluation.mapper;

import com.evaluation.dto.EmployerRegisterDto;
import com.evaluation.dto.JobSeekerRegisterDto;
import com.evaluation.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public static User mapDtoToEntity(JobSeekerRegisterDto dto) {
        User user = new User();
        user.setName(dto.name());
        user.setUsername(dto.username());
        user.setEmail(dto.email());
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
