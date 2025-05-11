package com.knowit.app.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;

@Entity
@Table(name = "enrollments")
public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @Column(name = "enrollment_date")
    private LocalDateTime enrollmentDate;

    @Column(name = "completion_date")
    private LocalDateTime completionDate;

    @DecimalMin(value = "0.00")
    @DecimalMax(value = "100.00")
    @Digits(integer = 3, fraction = 2)
    @Column(name = "progress_percentage")
    private BigDecimal progressPercentage = new BigDecimal("0.00");

    // Constructors
    public Enrollment() {
    }

    public Enrollment(Long id, User user, Course course, LocalDateTime enrollmentDate, 
                   LocalDateTime completionDate, BigDecimal progressPercentage) {
        this.id = id;
        this.user = user;
        this.course = course;
        this.enrollmentDate = enrollmentDate;
        this.completionDate = completionDate;
        this.progressPercentage = progressPercentage;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public LocalDateTime getEnrollmentDate() {
        return enrollmentDate;
    }

    public void setEnrollmentDate(LocalDateTime enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
    }

    public LocalDateTime getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(LocalDateTime completionDate) {
        this.completionDate = completionDate;
    }

    public BigDecimal getProgressPercentage() {
        return progressPercentage;
    }

    public void setProgressPercentage(BigDecimal progressPercentage) {
        this.progressPercentage = progressPercentage;
    }

    // Builder pattern
    public static EnrollmentBuilder builder() {
        return new EnrollmentBuilder();
    }

    public static class EnrollmentBuilder {
        private Long id;
        private User user;
        private Course course;
        private LocalDateTime enrollmentDate;
        private LocalDateTime completionDate;
        private BigDecimal progressPercentage = new BigDecimal("0.00");

        public EnrollmentBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public EnrollmentBuilder user(User user) {
            this.user = user;
            return this;
        }

        public EnrollmentBuilder course(Course course) {
            this.course = course;
            return this;
        }

        public EnrollmentBuilder enrollmentDate(LocalDateTime enrollmentDate) {
            this.enrollmentDate = enrollmentDate;
            return this;
        }

        public EnrollmentBuilder completionDate(LocalDateTime completionDate) {
            this.completionDate = completionDate;
            return this;
        }

        public EnrollmentBuilder progressPercentage(BigDecimal progressPercentage) {
            this.progressPercentage = progressPercentage;
            return this;
        }

        public Enrollment build() {
            return new Enrollment(id, user, course, enrollmentDate, completionDate, progressPercentage);
        }
    }
}