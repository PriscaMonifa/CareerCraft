package com.app.dao_impl;

import com.app.dao.EmployerDao;
import com.app.model.Employer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Component;

@Component
public class EmployerDaoImpl implements EmployerDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Employer getByUsername(String employerUsername) {
        String sql = "select e from Employer e where e.user.username = ?1";
        TypedQuery<Employer> query =
                entityManager.createQuery(sql, Employer.class);
        query.setParameter(1, employerUsername);
        return query.getSingleResult();
    }
}
