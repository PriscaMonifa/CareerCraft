package com.app;

import com.app.config.AppConfig;
import com.app.dao.AuthDao;
import com.app.dao.JobDao;
import com.app.dao_impl.AuthDaoImpl;
import com.app.exceptions.InvalidOwnershipException;
import com.app.exceptions.ResourceNotFoundException;
import com.app.model.Job;
import com.app.model.User;
import jakarta.persistence.NoResultException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Scanner;

public class App {

    public static void main(String[] args) {

        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);
        AuthDao authDao = context.getBean(AuthDaoImpl.class);
        JobDao jobDao = context.getBean(JobDao.class);

        Scanner sc = new Scanner(System.in);
        System.out.println("----------Career Crafter LOGIN---------");
        System.out.println("Enter Username ");
        String username = sc.next();
        System.out.println("Enter Password ");
        String password = sc.next();

        try {

            User user = authDao.login(username, password);
            switch(user.getRole().toString()) {
                case "EMPLOYER":

                    System.out.println("Welcome " + username);
                    while(true) {
                        System.out.println("1. Add Job");
                        System.out.println("2. Delete Job by id");
                        System.out.println("3. Fetch all Jobs");
                        System.out.println("4. Update Job");
                        System.out.println("0. Exit ");

                        int op = sc.nextInt();
                        if(op == 0)
                            break;

                        switch(op) {
                            case 1:
                                sc.nextLine();
                                System.out.println("Enter Job Title");
                                String jobTitle = sc.nextLine();
                                System.out.println("Enter Job Description");
                                String jobDescription = sc.nextLine();
                                System.out.println("Enter Salary Package");
                                double salaryPack = sc.nextDouble();
                                sc.nextLine();
                                System.out.println("Enter Required Skills");
                                String requiredSkills = sc.nextLine();

                                Job job = new Job();

                                job.setJobTitle(jobTitle);
                                job.setJobDescription(jobDescription);
                                job.setSalaryPack(salaryPack);
                                job.setRequiredSkills(requiredSkills);
                                jobDao.save(job, username);
                                System.out.println("Job added...");
                                break;

                            case 2:
                                System.out.println("Enter Job id to delete");
                                int id = sc.nextInt();
                                try {
                                    jobDao.delete(id, username);
                                    System.out.println("Job deleted successfully");
                                }
                                catch(ResourceNotFoundException | InvalidOwnershipException e) {
                                    System.out.println(e.getMessage());
                                }
                                break;

                            case 3:
                                System.out.println("----------ALL Jobs--------");
                                jobDao.findAll(username).forEach(System.out::println);
                                break;

                            case 4:
                                System.out.println("Enter Job id to update");
                                id = sc.nextInt();

                                try {
                                    Job jobToUpdate = jobDao.getById(id, username);

                                    System.out.println("Existing Job Record");
                                    System.out.println("Enter values for update..");
                                    sc.nextLine();
                                    System.out.println("Enter Job Title");
                                    jobTitle = sc.nextLine();
                                    System.out.println("Enter Job Description");
                                    jobDescription = sc.nextLine();
                                    System.out.println("Enter Salary Package");
                                    salaryPack = sc.nextDouble();
                                    sc.nextLine();
                                    System.out.println("Enter Required Skills");
                                    requiredSkills = sc.nextLine();

                                    jobToUpdate.setJobTitle(jobTitle);
                                    jobToUpdate.setJobDescription(jobDescription);
                                    jobToUpdate.setSalaryPack(salaryPack);
                                    jobToUpdate.setRequiredSkills(requiredSkills);
                                    jobDao.update(jobToUpdate);
                                    System.out.println("Record updated");
                                }
                                catch(ResourceNotFoundException |
                                      InvalidOwnershipException e) {
                                    System.out.println(e.getMessage());
                                }
                                break;
                        }
                    }
                    break;

                default:
                    break;
            }
        } catch(NoResultException e) {
            System.out.println("Invalid Credentials");
        }
        context.close();
    }
}