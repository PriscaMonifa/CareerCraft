package com.app.dao_impl;

import com.app.dao.JobDao;
import com.app.exceptions.InvalidOwnershipException;
import com.app.exceptions.ResourceNotFoundException;
import com.app.model.Employer;
import com.app.model.Job;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional
public class JobDaoImpl implements JobDao {

    @PersistenceContext
    private EntityManager entityManager;
    private EmployerDaoImpl employerDao;

    @Autowired
    public void setEmployerDao(EmployerDaoImpl employerDao) {
        this.employerDao = employerDao;
    }

    @Override
    public List<Job> findAll(String employerUsername) {
        TypedQuery<Job> query = entityManager.createQuery(
                "select j from Job j where j.employer.user.username=:username",
                Job.class
        );
        query.setParameter("username", employerUsername);
        return query.getResultList();
    }

    @Override
    public void save(Job job, String employerUsername) {
        Employer employer = employerDao.getByUsername(employerUsername);
        // attach employer to job
        job.setEmployer(employer);
        entityManager.persist(job);
    }

    @Override
    public Job getById(int id, String employerUsername) {
        Job job = entityManager.find(Job.class, id);
        if(job == null)
            throw new ResourceNotFoundException("Invalid id given..");
        // ownership check
        if(!(job.getEmployer().getUser().getUsername().equals(employerUsername))) {
            throw new InvalidOwnershipException("You do not own this job");
        }
        return job;
    }

    @Override
    public void update(Job job) {
        entityManager.merge(job);
    }

    @Override
    public void delete(int id, String employerUsername) {
        Job job = entityManager.find(Job.class, id);
        if(job == null)
            throw new ResourceNotFoundException("Invalid id given..");
        // ownership check
        getById(id,employerUsername);
        entityManager.remove(job);
    }
}