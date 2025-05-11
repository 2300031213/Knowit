package com.knowit.app.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "practice_tests")
public class PracticeTest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @NotBlank
    @Size(max = 100)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "time_limit_minutes")
    private Integer timeLimitMinutes;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @JsonIgnore
    @OneToMany(mappedBy = "test", cascade = CascadeType.ALL)
    private List<TestQuestion> questions;

    // Constructors
    public PracticeTest() {
    }

    public PracticeTest(Long id, Course course, String title, String description, 
                    Integer timeLimitMinutes, LocalDateTime createdAt, List<TestQuestion> questions) {
        this.id = id;
        this.course = course;
        this.title = title;
        this.description = description;
        this.timeLimitMinutes = timeLimitMinutes;
        this.createdAt = createdAt;
        this.questions = questions;
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getTimeLimitMinutes() {
        return timeLimitMinutes;
    }

    public void setTimeLimitMinutes(Integer timeLimitMinutes) {
        this.timeLimitMinutes = timeLimitMinutes;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<TestQuestion> getQuestions() {
        return questions;
    }

    public void setQuestions(List<TestQuestion> questions) {
        this.questions = questions;
    }

    // Builder pattern
    public static PracticeTestBuilder builder() {
        return new PracticeTestBuilder();
    }

    public static class PracticeTestBuilder {
        private Long id;
        private Course course;
        private String title;
        private String description;
        private Integer timeLimitMinutes;
        private LocalDateTime createdAt;
        private List<TestQuestion> questions;

        public PracticeTestBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public PracticeTestBuilder course(Course course) {
            this.course = course;
            return this;
        }

        public PracticeTestBuilder title(String title) {
            this.title = title;
            return this;
        }

        public PracticeTestBuilder description(String description) {
            this.description = description;
            return this;
        }

        public PracticeTestBuilder timeLimitMinutes(Integer timeLimitMinutes) {
            this.timeLimitMinutes = timeLimitMinutes;
            return this;
        }

        public PracticeTestBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public PracticeTestBuilder questions(List<TestQuestion> questions) {
            this.questions = questions;
            return this;
        }

        public PracticeTest build() {
            return new PracticeTest(id, course, title, description, timeLimitMinutes, createdAt, questions);
        }
    }
}