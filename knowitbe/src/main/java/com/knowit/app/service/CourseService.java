
package com.knowit.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.knowit.app.exception.ResourceNotFoundException;
import com.knowit.app.model.Course;
import com.knowit.app.model.CourseContent;
import com.knowit.app.repository.CourseContentRepository;
import com.knowit.app.repository.CourseRepository;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CourseContentRepository contentRepository;

    @Transactional(readOnly = true)
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Course getCourseById(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course", "id", id));
    }

    @Transactional(readOnly = true)
    public List<Course> getCoursesByCategory(String category) {
        return courseRepository.findByCategory(category);
    }

    @Transactional(readOnly = true)
    public List<Course> searchCoursesByTitle(String title) {
        return courseRepository.findByTitleContainingIgnoreCase(title);
    }

    @Transactional(readOnly = true)
    public List<CourseContent> getCourseContents(Long courseId) {
        Course course = getCourseById(courseId);
        return contentRepository.findByCourseOrderByOrderIndex(course);
    }

    @Transactional(readOnly = true)
    public CourseContent getCourseContent(Long contentId) {
        return contentRepository.findById(contentId)
                .orElseThrow(() -> new ResourceNotFoundException("CourseContent", "id", contentId));
    }

    // Additional methods for managing courses would go here
}
