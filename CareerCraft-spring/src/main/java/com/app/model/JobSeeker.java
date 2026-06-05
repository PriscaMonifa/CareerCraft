package com.app.model;

public class JobSeeker {
    private int seekerId;
    private String qualification;
    private String title;
    private String skills;
    private int experience;

    public JobSeeker() {
    }

    public JobSeeker(int seekerId, String qualification, String title, String skills, int experience) {
        this.seekerId = seekerId;
        this.qualification = qualification;
        this.title = title;
        this.skills = skills;
        this.experience = experience;
    }

    public int getSeekerId() {
        return seekerId;
    }

    public void setSeekerId(int seekerId) {
        this.seekerId = seekerId;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    @Override
    public String toString() {
        return "JobSeeker{" +
                "seekerId=" + seekerId +
                ", qualification='" + qualification + '\'' +
                ", title='" + title + '\'' +
                ", skills='" + skills + '\'' +
                ", experience=" + experience +
                '}';
    }
}
