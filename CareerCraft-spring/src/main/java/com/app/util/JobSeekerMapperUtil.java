package com.app.util;

import com.app.model.JobSeeker;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class JobSeekerMapperUtil implements RowMapper<JobSeeker> {

    @Override
    public JobSeeker mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new JobSeeker(
                rs.getInt("seekerId"),
                rs.getString("qualification"),
                rs.getString("title"),
                rs.getString("skills"),
                rs.getInt("experience")
        );
    }
}
