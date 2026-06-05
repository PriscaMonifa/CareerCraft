package com.app.dao;

import com.app.model.Employer;

public interface EmployerDao {
    Employer getByUsername(String employerUsername);
}
