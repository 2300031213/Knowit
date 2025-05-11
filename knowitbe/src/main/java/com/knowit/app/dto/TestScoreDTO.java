package com.knowit.app.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TestScoreDTO {
    private Long testId;
    private String testTitle;
    private BigDecimal bestScore;
    private Integer attempts;
    private LocalDateTime lastAttemptDate;

    // Getters and Setters
    public Long getTestId() {
        return testId;
    }

    public void setTestId(Long testId) {
        this.testId = testId;
    }

    public String getTestTitle() {
        return testTitle;
    }

    public void setTestTitle(String testTitle) {
        this.testTitle = testTitle;
    }

    public BigDecimal getBestScore() {
        return bestScore;
    }

    public void setBestScore(BigDecimal bestScore) {
        this.bestScore = bestScore;
    }

    public Integer getAttempts() {
        return attempts;
    }

    public void setAttempts(Integer attempts) {
        this.attempts = attempts;
    }

    public LocalDateTime getLastAttemptDate() {
        return lastAttemptDate;
    }

    public void setLastAttemptDate(LocalDateTime lastAttemptDate) {
        this.lastAttemptDate = lastAttemptDate;
    }
}