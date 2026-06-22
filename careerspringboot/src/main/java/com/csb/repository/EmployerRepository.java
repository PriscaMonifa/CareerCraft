package com.csb.repository;

import com.csb.model.Employer;
import com.csb.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface EmployerRepository extends JpaRepository<Employer,Integer> {
    Optional<Employer> findByUser(User user);
}
