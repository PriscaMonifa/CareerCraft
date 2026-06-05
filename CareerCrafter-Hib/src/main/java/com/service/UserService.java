package com.service;

import com.exception.ResourceNotFoundException;
import com.model.User;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class UserService {
    private final Session session;
    public UserService(Session session) {
        this.session=session;
    }

    public void insert(User user) {
        Transaction tx= session.beginTransaction();
        session.persist(user);
        tx.commit();
    }

    public void deleteUser(int userId) {
        Transaction tx=session.beginTransaction();
        session.createMutationQuery("delete from User where userId=:userId")
                .setParameter("userId",userId)
                        .executeUpdate();
        tx.commit();
    }

    public List<User> getAllUsers() {
        Transaction tx=session.beginTransaction();
        List<User> list=session.createQuery("from User",User.class).list();
        tx.commit();
        return list;
    }


    public User getByUserId(int userId) {
        Transaction tx= session.beginTransaction();
        User user=session.find(User.class,userId);
        tx.commit();
        if(user==null){
            throw new ResourceNotFoundException("Invalid userId given...");
        }
        return user;
    }

    public void applyJob(int jobId, String email) {
    }
}
