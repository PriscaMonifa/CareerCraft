package com.evaluation.repository;

import com.evaluation.model.Application;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRepository extends JpaRepository<Application,Integer> {
    Page<Application> findByJobSeekerId(int seekerId, Pageable pageable);
}
