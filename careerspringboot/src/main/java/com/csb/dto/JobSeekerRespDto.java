package com.csb.dto;

import com.csb.enums.JobCategory;

public record JobSeekerRespDto(
        int seekerId,
        String name,
        String email,
        String qualification,
        String education,
        JobCategory preferredCategory,
        String contact,
        String skills,
        int experience,
        String location,
        String socialUrl,
        String resumeUrl

) {
}
