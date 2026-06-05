package com.service;

import com.model.JobSeeker;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class JobSeekerService {

    private final Session session;

    public JobSeekerService(Session session) {
        this.session = session;
    }
    public JobSeeker getByEmail(String email) {
        Transaction tx = session.beginTransaction();
        JobSeeker seeker = session.createQuery("select js from JobSeeker js where js.user.email=:email", JobSeeker.class)
                        .setParameter("email", email)
                        .getSingleResult();

        tx.commit();
        return seeker;
    }
}