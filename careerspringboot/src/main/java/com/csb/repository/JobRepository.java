package com.csb.repository;

import com.csb.enums.JobCategory;
import com.csb.model.Employer;
import com.csb.model.Job;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface JobRepository extends JpaRepository<Job,Integer> {
    Page<Job> findAllByJobTitle(String jobTitle, Pageable pageable);

    Page<Job> findByCategoryAndActiveTrue(JobCategory category, Pageable pageable);

    @Query("""
       SELECT j FROM Job j
       where j.active=?1
""")
    Page<Job> findAllActive(boolean b, Pageable pageable);

    List<Job> findAllByEmployerUserUsername(String employerUserName);

    List<Job> findAllByEmployerUserUsernameAndActive(String employerUserName, Boolean active);

    Page<Job> findByEmployer(Employer employer, Pageable pageable);

    Page<Job> findAllByActiveTrue(Pageable pageable);

    Page<Job>findByJobTitleContainingIgnoreCaseAndEmployer_CompanyLocationContainingIgnoreCase(String jobTitle, String companyLocation, Pageable pageable);
}
