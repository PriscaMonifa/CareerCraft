package com.service;

import com.exception.InvalidOwnershipException;
import com.exception.ResourceNotFoundException;
//import com.model.Application;
import com.model.Employer;
import com.model.Job;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class JobService {
    private final Session session;
    private final EmployerService employerService;

    public JobService(Session session) {
        this.session = session;
        employerService = new EmployerService(session);
        JobSeekerService seekerService = new JobSeekerService(session);
    }

    public void addJob(Job job, String employerEmail) {

        Employer employer = employerService.getByEmail(employerEmail);
        job.setEmployer(employer);
        Transaction tx = session.beginTransaction();
        session.persist(job);
        tx.commit();
    }

    public List<Job> viewJobs() {
        Transaction tx = session.beginTransaction();
        List<Job> jobs = session.createQuery("from Job", Job.class).list();
        tx.commit();
        return jobs;
    }

    public Job getById(int jobId) {
        Transaction tx = session.beginTransaction();
        Job job = session.find(Job.class, jobId);
        tx.commit();
        if(job == null)
            throw new ResourceNotFoundException("Invalid Job Id");
        return job;
    }

    public void deleteJob(int jobId, String employerEmail) {
        Transaction tx = session.beginTransaction();
        Job job = session.find(Job.class, jobId);
        tx.commit();
        if(job == null)
            throw new ResourceNotFoundException("Job Id Invalid");
        Employer employer = employerService.getByEmail(employerEmail);

        if(job.getEmployer().getEmployerId() != employer.getEmployerId()) {

            throw new InvalidOwnershipException("Employer does not own this job");
        }
        tx = session.beginTransaction();
        session.remove(job);
        tx.commit();
    }

    /*public void applyJob(int jobId, String seekerEmail) {

        Job job = getById(jobId);
        JobSeeker seeker = seekerService.getByEmail(seekerEmail);

        Application application = new Application();
        application.setJob(job);
        application.setSeeker(seeker);
        application.setApplicationStatus("APPLIED");
        Transaction tx = session.beginTransaction();
        session.persist(application);
        tx.commit();
    }*/
}
