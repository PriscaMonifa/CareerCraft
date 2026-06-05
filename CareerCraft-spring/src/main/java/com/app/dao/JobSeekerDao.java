package com.app.dao;

import com.app.exceptions.ResourceNotFoundException;
import com.app.model.JobSeeker;

import java.util.List;

public interface JobSeekerDao {
    void insert(JobSeeker jobSeeker);
    List<JobSeeker> getAll();
    JobSeeker getById(int id);
    void deleteById(int id) throws ResourceNotFoundException;
    void update(JobSeeker jobSeeker);

}
