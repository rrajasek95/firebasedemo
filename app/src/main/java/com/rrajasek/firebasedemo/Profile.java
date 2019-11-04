package com.rrajasek.firebasedemo;

import java.util.List;

public class Profile {
    private String cruzId;
    private String name;
    private String position;
    private Long yearJoined;
    private List<String> coursesTaken;

    public Profile() {

    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCoursesTaken(List<String> coursesTaken) {
        this.coursesTaken = coursesTaken;
    }

    public void setCruzId(String cruzId) {
        this.cruzId = cruzId;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setYearJoined(Long yearJoined) {
        this.yearJoined = yearJoined;
    }

    public Profile(String cruzId, String name, String position, Long yearJoined, List<String> coursesTaken) {
        this.cruzId = cruzId;
        this.name = name;
        this.position = position;
        this.yearJoined = yearJoined;
        this.coursesTaken = coursesTaken;
    }

    public String getCruzId() {
        return cruzId;
    }

    public String getName() {
        return name;
    }

    public String getPosition() {
        return position;
    }

    public Long getYearJoined() {
        return yearJoined;
    }

    public List<String> getCoursesTaken() {
        return coursesTaken;
    }
}
