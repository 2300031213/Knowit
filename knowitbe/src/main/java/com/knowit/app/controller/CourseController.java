package com.knowit.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.knowit.app.model.Course;
import com.knowit.app.model.CourseContent;
import com.knowit.app.service.CourseService;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping
    public ResponseEntity<List<Course>> getAllCourses() {
        return ResponseEntity.ok(courseService.getAllCourses());
    }

    @GetMapping("/public")
    public ResponseEntity<List<Course>> getPublicCourses() {
        return ResponseEntity.ok(courseService.getAllCourses());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable Long id) {
        return ResponseEntity.ok(courseService.getCourseById(id));
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<Course>> getCoursesByCategory(@PathVariable String category) {
        return ResponseEntity.ok(courseService.getCoursesByCategory(category));
    }

    @GetMapping("/search")
    public ResponseEntity<List<Course>> searchCourses(@RequestParam String title) {
        return ResponseEntity.ok(courseService.searchCoursesByTitle(title));
    }

    @GetMapping("/{courseId}/contents")
    public ResponseEntity<List<CourseContent>> getCourseContents(@PathVariable Long courseId) {
        return ResponseEntity.ok(courseService.getCourseContents(courseId));
    }

    @GetMapping("/{courseId}/contents/{contentId}")
    public ResponseEntity<CourseContent> getCourseContent(@PathVariable Long courseId, @PathVariable Long contentId) {
        return ResponseEntity.ok(courseService.getCourseContent(contentId));
    }
    
    // Additional endpoints for instructors and admins to manage courses would go here
    @PreAuthorize("hasRole('INSTRUCTOR') or hasRole('ADMIN')")
    @GetMapping("/manage")
    public ResponseEntity<List<Course>> getCoursesForManagement() {
        return ResponseEntity.ok(courseService.getAllCourses());
    }
}