package com.csb.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Employer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int employerId;

    @Column(nullable = false)
    private String companyName;

    @Column(nullable = false)
    private String companyLocation;

    @Column(nullable = false)
    private String contactNumber;

    @OneToOne
    private User user;

}
