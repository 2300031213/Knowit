
package com.knowit.app.dto;

import java.math.BigDecimal;
import java.util.List;

public class CourseProgressDTO {
    private Long courseId;
    private String courseTitle;
    private BigDecimal progressPercentage;
    private Boolean completed;
    private List<TestScoreDTO> testScores;

    // Getters and Setters
    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public BigDecimal getProgressPercentage() {
        return progressPercentage;
    }

    public void setProgressPercentage(BigDecimal progressPercentage) {
        this.progressPercentage = progressPercentage;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public List<TestScoreDTO> getTestScores() {
        return testScores;
    }

    public void setTestScores(List<TestScoreDTO> testScores) {
        this.testScores = testScores;
    }
}