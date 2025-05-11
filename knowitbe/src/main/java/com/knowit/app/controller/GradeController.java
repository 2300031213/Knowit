package com.knowit.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.knowit.app.dto.CourseProgressDTO;
import com.knowit.app.dto.UserGradesDTO;
import com.knowit.app.model.TestAttempt;
import com.knowit.app.model.User;
import com.knowit.app.service.GradeService;

@RestController
@RequestMapping("/api/grades")
public class GradeController {

    @Autowired
    private GradeService gradeService;

    @GetMapping("/me")
    public ResponseEntity<UserGradesDTO> getUserGrades(@AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(gradeService.getUserGrades(currentUser));
    }

    @GetMapping("/courses/{courseId}")
    public ResponseEntity<CourseProgressDTO> getCourseProgress(
            @PathVariable Long courseId,
            @AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(gradeService.getCourseProgress(courseId, currentUser));
    }

    @GetMapping("/tests/{testId}/attempts")
    public ResponseEntity<List<TestAttempt>> getTestAttempts(
            @PathVariable Long testId,
            @AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(gradeService.getTestAttempts(testId, currentUser));
    }

    @GetMapping("/tests/{testId}/best")
    public ResponseEntity<TestAttempt> getBestTestAttempt(
            @PathVariable Long testId,
            @AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(gradeService.getBestTestAttempt(testId, currentUser));
    }
}