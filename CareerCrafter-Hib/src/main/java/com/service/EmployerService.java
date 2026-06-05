package com.service;

import com.model.Employer;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class EmployerService {

    private final Session session;
    public EmployerService(Session session) {
        this.session = session;
    }

    public Employer getByEmail(String email) {
        Transaction tx = session.beginTransaction();
        Employer employer = session.createQuery("select e from Employer e where e.user.email=:email", Employer.class)
                        .setParameter("email", email)
                        .getSingleResult();
        tx.commit();
        return employer;
    }
}
