package com.csb.service;

import com.csb.dto.JobDto;
import com.csb.enums.JobCategory;
import com.csb.enums.JobType;
import com.csb.exception.ResourceNotFoundException;
import com.csb.model.Employer;
import com.csb.model.Job;
import com.csb.repository.JobRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class JobServiceTest {

    @Mock
    private JobRepository jobRepository;

    @InjectMocks
    private JobService jobService;
    @Mock
    private EmployerService employerService;


    private Job job;
    private Job job1;

    @BeforeEach
    public void sampleJobs(){

        job=new Job();
        job.setJobId(1);
        job.setJobTitle("Backend Engineer");
        job.setSal(4000000.0);
        job.setActive(true);
        Employer employer = new Employer();
        employer.setCompanyLocation("Chennai");
        employer.setCompanyName("Zoho");
        job.setEmployer(employer);

        job1=new Job();
        job1.setJobId(2);
        job1.setJobTitle("Frontend Engineer");
        job1.setSal(5000000.0);
        job1.setActive(true);
        Employer employer1 = new Employer();
        employer1.setCompanyLocation("Bangalore");
        employer1.setCompanyName("TCS");
        job1.setEmployer(employer1);


    }

    //GET_ALL
    @Test
    void getAllJobs_MustReturnJobs(){
        Page<Job> page=new PageImpl<>(List.of(job,job1));
        when(jobRepository.findAllActive(eq(true), any(Pageable.class))).thenReturn(page);
        Page<JobDto> actualCall=jobService.getAllWithPagination(1,4);
        assertThat(actualCall.getContent().getFirst().jobId()).isEqualTo(1);
        assertThat(actualCall.getContent().getFirst().jobTitle()).isEqualTo("Backend Engineer");
        assertThat(actualCall.getContent().getLast().companyLocation()).isEqualTo("Bangalore");
        assertThat(actualCall.getContent().getFirst().sal()).isEqualTo(4000000.0);
        assertThat(actualCall.getContent().getFirst().active()).isEqualTo(true);

    }

    @Test
    void getAllJobs_ReturnsEmpty(){
        Page<Job> page=new PageImpl<>(List.of());
        when(jobRepository.findAllActive(eq(true), any(Pageable.class))).thenReturn(page);
        Page<JobDto> actualCall=jobService.getAllWithPagination(1,4);
        assertThat(actualCall.isEmpty());


    }

    //GET_BY_ID
    @Test
    void getById_jobExists(){
        when(jobRepository.findById(1)).thenReturn(Optional.of(job));
        JobDto actualCall=jobService.getJobById(1);
        assertThat(actualCall.jobId()).isEqualTo(1);
        assertThat(actualCall.jobTitle()).isEqualTo("Backend Engineer");
        assertThat(actualCall.sal()).isEqualTo(4000000.0);
        assertThat(actualCall.active()).isEqualTo(true);
        assertThat(actualCall.companyLocation()).isEqualTo("Chennai");
        assertThat(actualCall.companyName()).isEqualTo("Zoho");
    }

    @Test
    void getById_jobDoesNotExist(){
        when(jobRepository.findById(1)).thenReturn(Optional.empty());
        assertThatThrownBy(()-> jobService.getJobById(1))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Invalid id..");

    }

    //ADD
    @Test
    void addJobs_MustReturnDto(){
        Employer emp=job.getEmployer();
        when(employerService.getById(1)).thenReturn(emp);
        JobDto dto=new JobDto(1,"Backend Engineer","Java Developer Role",4000000.0,
                "Java,Spring","Bangalore", JobType.FULL_TIME, JobCategory.SOFTWARE_DEVELOPMENT,
                "TCS", Instant.now(),Instant.now(),true);
        jobService.addJobWithEmployer(dto,1);
        verify(jobRepository, times(1)).save(any(Job.class));

    }
    @Test
    void addJobs_DoesNotExist(){
        when(employerService.getById(1)).thenThrow(new ResourceNotFoundException("Invalid Employer Id"));
        JobDto dto=new JobDto(1,"Backend Engineer","Java Developer Role",4000000.0,
                "Java,Spring","Bangalore", JobType.FULL_TIME, JobCategory.SOFTWARE_DEVELOPMENT,
                "TCS", Instant.now(),Instant.now(),true);
        assertThatThrownBy(()->jobService.addJobWithEmployer(dto,1)).isInstanceOf(ResourceNotFoundException.class).hasMessage("Invalid Employer Id");

    }

    //DELETE
    @Test
    void deleteJobs_MustDeleteAndReturnNothing(){
        when(jobRepository.findById(1)).thenReturn(Optional.of(job));
        doNothing().when(jobRepository).deleteById(1);
        jobService.deleteById(1);
        verify(jobRepository,times(1)).deleteById(1);

    }

    @Test
    void deleteJobs_DoesNotExist(){
        when(jobRepository.findById(1)).thenReturn(Optional.empty());
        assertThatThrownBy(()->jobService.deleteById(1)).isInstanceOf(ResourceNotFoundException.class).hasMessage("Invalid id..");

    }


}
