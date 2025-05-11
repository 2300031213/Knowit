package com.knowit.app.dto;

import java.util.List;

public class UserGradesDTO {
    private Long userId;
    private String username;
    private String fullName;
    private Integer totalCoursesEnrolled;
    private Integer totalCoursesCompleted;
    private List<CourseProgressDTO> courseProgress;

    // Getters and Setters
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Integer getTotalCoursesEnrolled() {
        return totalCoursesEnrolled;
    }

    public void setTotalCoursesEnrolled(Integer totalCoursesEnrolled) {
        this.totalCoursesEnrolled = totalCoursesEnrolled;
    }

    public Integer getTotalCoursesCompleted() {
        return totalCoursesCompleted;
    }

    public void setTotalCoursesCompleted(Integer totalCoursesCompleted) {
        this.totalCoursesCompleted = totalCoursesCompleted;
    }

    public List<CourseProgressDTO> getCourseProgress() {
        return courseProgress;
    }

    public void setCourseProgress(List<CourseProgressDTO> courseProgress) {
        this.courseProgress = courseProgress;
    }
}