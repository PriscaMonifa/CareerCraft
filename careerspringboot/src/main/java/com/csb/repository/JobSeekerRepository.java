package com.csb.repository;

import com.csb.model.JobSeeker;
import com.csb.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JobSeekerRepository extends JpaRepository<JobSeeker,Integer> {
    Optional<JobSeeker> findByUserUsername(String username);
    Optional<JobSeeker> findByUserId(int userId);

    Optional<JobSeeker> findByUser(User user);

    String user(User user);
}
