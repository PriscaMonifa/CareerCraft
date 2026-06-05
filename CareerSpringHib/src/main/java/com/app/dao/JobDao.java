package com.app.dao;

import com.app.model.Job;

import java.util.List;

public interface JobDao {
    List<Job> findAll(String employerUsername);
    void save(Job job,String employerUsername);
    Job getById(int id,String employerUsername);
    void update(Job job);
    void delete(int id, String employerUsername);


}
