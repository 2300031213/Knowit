package com.knowit.app.dto;

import java.util.Map;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class TestAttemptRequest {
    @NotNull
    private Map<Long, String> answers; // Question ID -> Selected Answer (A, B, C, or D)
}