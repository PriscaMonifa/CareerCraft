package com.app;

import com.app.config.AppConfig;
import com.app.dao.JobSeekerDao;
import com.app.dao_impl.JobSeekerDaoImpl;
import com.app.exceptions.ResourceNotFoundException;
import com.app.model.JobSeeker;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context
                = new AnnotationConfigApplicationContext(
                AppConfig.class
        );
        Scanner sc = new Scanner(System.in);
        JobSeekerDao jobSeekerDao = context.getBean(JobSeekerDaoImpl.class);

        while(true){
            System.out.println("1. Add Job Seeker");
            System.out.println("2. Delete Job Seeker By Id");
            System.out.println("3. Update Job Seeker");
            System.out.println("4. View All Job Seekers");
            System.out.println("5. Get Job Seeker By Id");
            System.out.println("0. Exit");
            int op = sc.nextInt();
            if(op == 0)
                break;

            switch(op){
                case 1:
                    JobSeeker jobSeeker = new JobSeeker();
                    sc.nextLine();
                    System.out.println("Enter Qualification");
                    jobSeeker.setQualification(sc.nextLine());
                    System.out.println("Enter Title");
                    jobSeeker.setTitle(sc.nextLine());
                    System.out.println("Enter Skills");
                    jobSeeker.setSkills(sc.nextLine());
                    System.out.println("Enter Experience");
                    jobSeeker.setExperience(sc.nextInt());
                    jobSeekerDao.insert(jobSeeker);
                    break;

                case 2:
                    System.out.println("Enter Id To Delete");
                    int id = sc.nextInt();
                    try {
                        jobSeekerDao.deleteById(id);
                    }
                    catch(ResourceNotFoundException e){
                        System.out.println(e.getMessage());
                    }
                    break;

                case 3:
                    System.out.println("Enter Job Seeker Id To Update");
                    try {
                        JobSeeker seeker = jobSeekerDao.getById(sc.nextInt());
                        System.out.println("Existing Record");
                        System.out.println(seeker);
                        System.out.println("Enter New Skills");
                        sc.nextLine();
                        String skills = sc.nextLine();
                        seeker.setSkills(skills);
                        jobSeekerDao.update(seeker);
                    }
                    catch(EmptyResultDataAccessException e){
                        System.out.println("Invalid Id");
                    }
                    break;

                case 4:
                    jobSeekerDao.getAll().forEach(System.out::println);
                    break;

                case 5:
                    System.out.println("Enter Id To Fetch Record");
                    id = sc.nextInt();
                    try {
                        JobSeeker seeker = jobSeekerDao.getById(id);
                        System.out.println(seeker);
                    }
                    catch(EmptyResultDataAccessException e){
                        System.out.println("Invalid Id");
                    }
                    break;
                default:
                    System.out.println("Invalid Choice");
            }
        }
        sc.close();
        context.close();
    }
}
