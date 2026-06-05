package com.controller;

import com.config.HibernateConfig;
import com.enums.Role;
import com.exception.InvalidRoleException;
import com.model.Job;
import com.model.User;
import com.service.AuthService;
import com.service.JobService;
import com.service.UserService;
import org.hibernate.Session;

import jakarta.persistence.NoResultException;

import java.util.List;
import java.util.Scanner;

public class MainClass {
    public static void main(String[] args) {
        Session session = HibernateConfig.getSessionFactory().openSession();

        Scanner sc = new Scanner(System.in);
        UserService userService = new UserService(session);
        AuthService authService = new AuthService(session);
        JobService jobService = new JobService(session);

        System.out.println("===== CAREER CRAFTER LOGIN =====");
        System.out.println("Enter Email");
        String email = sc.next();
        System.out.println("Enter Password");
        String password = sc.next();
        try {
            User user = authService.login(email, password);
            switch(user.getRole().toString()) {

                case "EMPLOYER":
                    System.out.println("===== EMPLOYER MENU =====");
                    while(true) {
                        System.out.println("1. Add Job");
                        System.out.println("2. View Jobs");
                        System.out.println("3. Delete Job");
                        System.out.println("0. Exit");
                        int op = sc.nextInt();
                        if(op == 0)
                            break;

                        switch(op) {
                            case 1:
                                Job job = new Job();
                                sc.nextLine();
                                System.out.println("Enter Job Title");
                                job.setJobTitle(sc.nextLine());
                                System.out.println("Enter Job Description");
                                job.setJobDescription(sc.nextLine());
                                System.out.println("Enter Salary Package");
                                job.setSalaryPack(sc.nextDouble());
                                sc.nextLine();
                                System.out.println("Enter Required Skills");
                                job.setRequiredSkills(sc.nextLine());
                                jobService.addJob(job, email);
                                System.out.println("Job Added Successfully...");
                                break;

                            case 2:
                                List<Job> jobs = jobService.viewJobs();
                                jobs.forEach(System.out::println);
                                break;

                            case 3:
                                System.out.println("Enter Job Id");
                                int jobId = sc.nextInt();
                                jobService.deleteJob(jobId, email);
                                System.out.println("Job Deleted Successfully");
                                break;

                            default:
                                System.out.println("Invalid Choice");
                        }
                    }
                    break;

                case "JOB_SEEKER":
                    System.out.println("===== JOB SEEKER MENU =====");
                    while(true) {
                        System.out.println("1. View Jobs");
                        System.out.println("2. Apply Job");
                        System.out.println("0. Exit");
                        int op = sc.nextInt();
                        if(op == 0)
                            break;

                        switch(op) {
                            case 1:
                                List<Job> jobs = jobService.viewJobs();
                                jobs.forEach(System.out::println);
                                break;

                            case 2:
                                System.out.println("Enter Job Id");
                                int jobId = sc.nextInt();
                                userService.applyJob(jobId, email);

                                System.out.println("Applied Successfully");
                                break;

                            default:
                                System.out.println("Invalid Choice");
                        }
                    }
                    break;
                default:
                    throw new RuntimeException("Invalid Role");
            }
        }
        catch(NoResultException e) {
            System.out.println("Invalid Credentials");
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
        sc.close();
        session.close();
    }
}
