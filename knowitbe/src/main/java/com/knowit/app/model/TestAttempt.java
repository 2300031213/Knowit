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
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "test_attempts")
public class TestAttempt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "test_id", nullable = false)
    private PracticeTest test;

    @NotNull
    @DecimalMin(value = "0.00")
    @Digits(integer = 5, fraction = 2)
    private BigDecimal score;

    @NotNull
    @Column(name = "total_questions")
    private Integer totalQuestions;

    @NotNull
    @Column(name = "correct_answers")
    private Integer correctAnswers;

    @Column(name = "attempt_date")
    private LocalDateTime attemptDate;

    // Constructors
    public TestAttempt() {
    }

    public TestAttempt(Long id, User user, PracticeTest test, BigDecimal score, 
                  Integer totalQuestions, Integer correctAnswers, LocalDateTime attemptDate) {
        this.id = id;
        this.user = user;
        this.test = test;
        this.score = score;
        this.totalQuestions = totalQuestions;
        this.correctAnswers = correctAnswers;
        this.attemptDate = attemptDate;
    }

    @PrePersist
    protected void onCreate() {
        attemptDate = LocalDateTime.now();
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

    public PracticeTest getTest() {
        return test;
    }

    public void setTest(PracticeTest test) {
        this.test = test;
    }

    public BigDecimal getScore() {
        return score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }

    public Integer getTotalQuestions() {
        return totalQuestions;
    }

    public void setTotalQuestions(Integer totalQuestions) {
        this.totalQuestions = totalQuestions;
    }

    public Integer getCorrectAnswers() {
        return correctAnswers;
    }

    public void setCorrectAnswers(Integer correctAnswers) {
        this.correctAnswers = correctAnswers;
    }

    public LocalDateTime getAttemptDate() {
        return attemptDate;
    }

    public void setAttemptDate(LocalDateTime attemptDate) {
        this.attemptDate = attemptDate;
    }

    // Builder pattern
    public static TestAttemptBuilder builder() {
        return new TestAttemptBuilder();
    }

    public static class TestAttemptBuilder {
        private Long id;
        private User user;
        private PracticeTest test;
        private BigDecimal score;
        private Integer totalQuestions;
        private Integer correctAnswers;
        private LocalDateTime attemptDate;

        public TestAttemptBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public TestAttemptBuilder user(User user) {
            this.user = user;
            return this;
        }

        public TestAttemptBuilder test(PracticeTest test) {
            this.test = test;
            return this;
        }

        public TestAttemptBuilder score(BigDecimal score) {
            this.score = score;
            return this;
        }

        public TestAttemptBuilder totalQuestions(Integer totalQuestions) {
            this.totalQuestions = totalQuestions;
            return this;
        }

        public TestAttemptBuilder correctAnswers(Integer correctAnswers) {
            this.correctAnswers = correctAnswers;
            return this;
        }

        public TestAttemptBuilder attemptDate(LocalDateTime attemptDate) {
            this.attemptDate = attemptDate;
            return this;
        }

        public TestAttempt build() {
            return new TestAttempt(id, user, test, score, totalQuestions, correctAnswers, attemptDate);
        }
    }
}