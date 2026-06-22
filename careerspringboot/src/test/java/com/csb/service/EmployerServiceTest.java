package com.csb.service;


import com.csb.dto.EmployerRegisterDto;
import com.csb.dto.EmployerRespDto;
import com.csb.exception.ResourceNotFoundException;
import com.csb.model.Employer;
import com.csb.model.User;
import com.csb.repository.EmployerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployerServiceTest {

    @Mock
    private EmployerRepository employerRepository;

    @InjectMocks
    private EmployerService employerService;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private UserService userService;

    private Employer employer;

    @BeforeEach
    public void SampleEmployerData(){

        employer = new Employer();
        employer.setEmployerId(1);
        employer.setCompanyName("Tech");
        User user = new User();
        user.setName("John");
        user.setEmail("john@gmail.com");
        user.setPassword("john123");
        employer.setUser(user);

    }

    //GET_BY_ID
    @Test
    void getById_employerExists(){
        when(employerRepository.findById(1)).thenReturn(Optional.of(employer));
        EmployerRespDto actualCall=employerService.getEmployerById(1);
        assertThat(actualCall.employerId()).isEqualTo(1);
        assertThat(actualCall.companyName()).isEqualTo("Tech");
        assertThat(actualCall.employerName()).isEqualTo("John");
        assertThat(actualCall.email()).isEqualTo("john@gmail.com");

    }

    @Test
    void getById_jobDoesNotExist(){
        when(employerRepository.findById(1)).thenReturn(Optional.empty());
        assertThatThrownBy(()-> employerService.getEmployerById(1))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Invalid Employer Id");

    }
    //ADD
    @Test
    void addEmployer_MustReturnDto(){
        EmployerRegisterDto dto=new EmployerRegisterDto("John","john@123","john@gmail.com","Tech","Mumbai","9874567462");
        when(passwordEncoder.encode(any())).thenReturn("encodedPassword");
        when(userService.save(any(User.class))).thenReturn(new User());
        employerService.postEmployer(dto);
        verify(userService, times(1)).save(any(User.class));
        verify(employerRepository, times(1)).save(any(Employer.class));

    }
    @Test
    void addJobs_DoesNotExist(){
        EmployerRegisterDto dto=new EmployerRegisterDto("John","john@123","john@gmail.com","Tech","Mumbai","9874567462");
        when(passwordEncoder.encode(any())).thenReturn("encodedPassword");
        when(userService.save(any(User.class))).thenThrow(new RuntimeException("User already exists"));
        assertThatThrownBy(() -> employerService.postEmployer(dto)).isInstanceOf(RuntimeException.class).hasMessage("User already exists");
    }

    //DELETE
    @Test
    void deleteEmployer_MustDeleteAndReturnNothing(){
        when(employerRepository.findById(1)).thenReturn(Optional.of(employer));
        doNothing().when(employerRepository).deleteById(1);
        employerService.delete(1);

    }

    @Test
    void deleteJobs_DoesNotExist(){
        when(employerRepository.findById(1)).thenReturn(Optional.empty());
        assertThatThrownBy(()->employerService.delete(1)).isInstanceOf(ResourceNotFoundException.class).hasMessage("Invalid Employer Id");

    }


}
