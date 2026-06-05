package com.app.dao_impl;

import com.app.dao.JobSeekerDao;
import com.app.exceptions.ResourceNotFoundException;
import com.app.model.JobSeeker;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JobSeekerDaoImpl implements JobSeekerDao {

    private final JdbcTemplate jdbcTemplate;
    public JobSeekerDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private RowMapper<JobSeeker> mapper() {
        return (rs, num) -> {
            return new JobSeeker(
                    rs.getInt("seekerId"),
                    rs.getString("qualification"),
                    rs.getString("title"),
                    rs.getString("skills"),
                    rs.getInt("experience")
            );
        };
    }

    @Override
    public void insert(JobSeeker jobSeeker) {
        String sql =
                "insert into jobseeker(qualification,title,skills,experience) " + "values(?,?,?,?)";
        jdbcTemplate.update(sql, jobSeeker.getQualification(), jobSeeker.getTitle(), jobSeeker.getSkills(),
                jobSeeker.getExperience());
        System.out.println("Job Seeker Added");
    }

    @Override
    public List<JobSeeker> getAll() {
        String sql = "select * from jobseeker";
        return jdbcTemplate.query(sql, mapper()
        );
    }

    @Override
    public JobSeeker getById(int id) {
        String sql = "select * from jobseeker where seekerId=?";
        return jdbcTemplate.queryForObject(sql,mapper(),id);
    }

    @Override
    public void deleteById(int id) throws ResourceNotFoundException {
        String sql = "delete from jobseeker " + "where seekerId=?";
        int numRow = jdbcTemplate.update(sql,id);
        if(numRow == 0)
            throw new ResourceNotFoundException("Invalid Id");
        System.out.println("Job Seeker Deleted");
    }

    @Override
    public void update(JobSeeker jobSeeker) {
        String sql = "update jobseeker set skills=? where seekerId=?";
        jdbcTemplate.update(sql, jobSeeker.getSkills(), jobSeeker.getSeekerId());
        System.out.println("Job Seeker Updated");
    }
}