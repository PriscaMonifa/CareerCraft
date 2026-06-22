package com.csb.model;

import com.csb.enums.JobCategory;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class JobSeeker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int seekerId;

    private String qualification;

    private String skills;

    private int experience;

    private String education;

    private String contact;

    private String location;

    @Enumerated(EnumType.STRING)
    private JobCategory preferredCategory;

    private String socialUrl;

    private String resumeUrl;

    @OneToOne
    private User user;

}
