package com.service;

import com.model.User;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class AuthService {

    private final Session session;
    public AuthService(Session session) {
        this.session = session;
    }

    public User login(String email, String password) {
        Transaction tx = session.beginTransaction();
        User user = session.createQuery("from User where email=:email and password=:password", User.class)
                        .setParameter("email", email)
                        .setParameter("password", password)
                        .getSingleResult();
        tx.commit();
        return user;
    }
}
