package com.knowit.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.knowit.app.dto.TestAttemptRequest;
import com.knowit.app.dto.TestAttemptResponse;
import com.knowit.app.model.PracticeTest;
import com.knowit.app.model.TestAttempt;
import com.knowit.app.model.TestQuestion;
import com.knowit.app.model.User;
import com.knowit.app.service.PracticeService;

@RestController
@RequestMapping("/api/practice")
public class PracticeController {

    @Autowired
    private PracticeService practiceService;

    @GetMapping("/courses/{courseId}/tests")
    public ResponseEntity<List<PracticeTest>> getCourseTests(@PathVariable Long courseId) {
        return ResponseEntity.ok(practiceService.getTestsByCourseId(courseId));
    }

    @GetMapping("/tests/{testId}")
    public ResponseEntity<PracticeTest> getTestById(@PathVariable Long testId) {
        return ResponseEntity.ok(practiceService.getTestById(testId));
    }

    @GetMapping("/tests/{testId}/questions")
    public ResponseEntity<List<TestQuestion>> getTestQuestions(@PathVariable Long testId) {
        return ResponseEntity.ok(practiceService.getQuestionsByTestId(testId));
    }

    @PostMapping("/tests/{testId}/submit")
    public ResponseEntity<TestAttemptResponse> submitTestAttempt(
            @PathVariable Long testId,
            @RequestBody TestAttemptRequest testAttemptRequest,
            @AuthenticationPrincipal User currentUser) {
        TestAttempt attempt = practiceService.submitTestAttempt(testId, testAttemptRequest, currentUser);
        TestAttemptResponse response = new TestAttemptResponse();
        response.setAttemptId(attempt.getId());
        response.setScore(attempt.getScore());
        response.setTotalQuestions(attempt.getTotalQuestions());
        response.setCorrectAnswers(attempt.getCorrectAnswers());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user/attempts")
    public ResponseEntity<List<TestAttempt>> getUserAttempts(@AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(practiceService.getAttemptsByUser(currentUser));
    }

    @GetMapping("/tests/{testId}/attempts")
    public ResponseEntity<List<TestAttempt>> getTestAttemptsByUser(
            @PathVariable Long testId,
            @AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(practiceService.getAttemptsByUserAndTest(currentUser, testId));
    }
}