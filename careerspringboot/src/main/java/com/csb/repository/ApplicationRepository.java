package com.csb.repository;

import com.csb.dto.CategoryChartDto;
import com.csb.enums.ApplicationStatus;
import com.csb.model.Application;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application,Integer> {

    Page<Application> findByJobJobId(int jobId, Pageable pageable);
    Page<Application> findByJobSeekerSeekerId(int seekerId, Pageable pageable);

    List<Application> findAllByJobEmployerUserUsernameAndStatus(String employerUsername, ApplicationStatus status);
    List<Application> findAllByJobEmployerUserUsername(String employerUsername);


    Page<Application> findByJobSeekerUserUsername(String username, Pageable pageable);

    List<Application> findAllByJobSeekerUserUsername(String username);

    List<Application> findAllByJobSeekerUserUsernameAndStatus(
            String username,
            ApplicationStatus status
    );
    long countByJobSeekerUserUsernameAndStatus(
            String username,
            ApplicationStatus status
    );
    @Query("""
    SELECT a FROM Application a
    WHERE a.job.employer.id = :employerId
    ORDER BY a.appliedDate DESC
""")
    Page<Application> findRecentApplications(int employerId, Pageable pageable);

    @Query("""
    SELECT new com.csb.dto.CategoryChartDto(
        j.category,
        COUNT(a)
    )
    FROM Application a
    JOIN a.job j
    WHERE j.employer.employerId = :employerId
    GROUP BY j.category
    ORDER BY COUNT(a) DESC
""")
    List<CategoryChartDto> getApplicationsByCategory(int employerId);

    Page<Application> findByJobSeekerUserUsernameAndStatus(
            String username,
            ApplicationStatus status,
            Pageable pageable);
}
