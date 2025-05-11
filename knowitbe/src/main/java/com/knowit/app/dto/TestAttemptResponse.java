package com.knowit.app.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class TestAttemptResponse {
    private Long attemptId;
    private BigDecimal score;
    private Integer totalQuestions;
    private Integer correctAnswers;
}