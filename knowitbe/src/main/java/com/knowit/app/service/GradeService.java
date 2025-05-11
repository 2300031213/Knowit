
package com.knowit.app.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.knowit.app.dto.CourseProgressDTO;
import com.knowit.app.dto.TestScoreDTO;
import com.knowit.app.dto.UserGradesDTO;
import com.knowit.app.exception.ResourceNotFoundException;
import com.knowit.app.model.Course;
import com.knowit.app.model.Enrollment;
import com.knowit.app.model.PracticeTest;
import com.knowit.app.model.TestAttempt;
import com.knowit.app.model.User;
import com.knowit.app.repository.CourseRepository;
import com.knowit.app.repository.EnrollmentRepository;
import com.knowit.app.repository.PracticeTestRepository;
import com.knowit.app.repository.TestAttemptRepository;

@Service
public class GradeService {

    @Autowired
    private TestAttemptRepository attemptRepository;

    @Autowired
    private PracticeTestRepository testRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Transactional(readOnly = true)
    public UserGradesDTO getUserGrades(User user) {
        UserGradesDTO gradesDTO = new UserGradesDTO();
        gradesDTO.setUserId(user.getId());
        gradesDTO.setUsername(user.getUsername());
        gradesDTO.setFullName(user.getFullName());

        // Get user enrollments
        List<Enrollment> enrollments = enrollmentRepository.findByUser(user);
        gradesDTO.setTotalCoursesEnrolled(enrollments.size());
        
        // Count completed courses
        int completedCourses = (int) enrollments.stream()
                .filter(e -> e.getCompletionDate() != null)
                .count();
        gradesDTO.setTotalCoursesCompleted(completedCourses);

        // Get course progress for all enrolled courses
        List<CourseProgressDTO> courseProgressList = enrollments.stream()
                .map(enrollment -> {
                    return getCourseProgress(enrollment.getCourse().getId(), user);
                })
                .collect(Collectors.toList());
        
        gradesDTO.setCourseProgress(courseProgressList);

        return gradesDTO;
    }

    @Transactional(readOnly = true)
    public CourseProgressDTO getCourseProgress(Long courseId, User user) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course", "id", courseId));
        
        CourseProgressDTO progressDTO = new CourseProgressDTO();
        progressDTO.setCourseId(course.getId());
        progressDTO.setCourseTitle(course.getTitle());
        
        // Get enrollment if exists
        Optional<Enrollment> enrollmentOpt = enrollmentRepository.findByUserAndCourse(user, course);
        
        if (enrollmentOpt.isPresent()) {
            Enrollment enrollment = enrollmentOpt.get();
            progressDTO.setProgressPercentage(enrollment.getProgressPercentage());
            progressDTO.setCompleted(enrollment.getCompletionDate() != null);
        } else {
            progressDTO.setProgressPercentage(BigDecimal.ZERO);
            progressDTO.setCompleted(false);
        }
        
        // Get all tests for the course
        List<PracticeTest> tests = testRepository.findByCourse(course);
        
        // For each test, get the best score and attempt count
        List<TestScoreDTO> testScores = new ArrayList<>();
        
        for (PracticeTest test : tests) {
            List<TestAttempt> attempts = attemptRepository.findByUserAndTest(user, test);
            
            if (!attempts.isEmpty()) {
                TestScoreDTO scoreDTO = new TestScoreDTO();
                scoreDTO.setTestId(test.getId());
                scoreDTO.setTestTitle(test.getTitle());
                scoreDTO.setAttempts(attempts.size());
                
                // Find best score
                TestAttempt bestAttempt = attempts.stream()
                        .max(Comparator.comparing(TestAttempt::getScore))
                        .orElse(attempts.get(0));
                
                scoreDTO.setBestScore(bestAttempt.getScore());
                
                // Find last attempt date
                TestAttempt lastAttempt = attempts.stream()
                        .max(Comparator.comparing(TestAttempt::getAttemptDate))
                        .orElse(attempts.get(0));
                
                scoreDTO.setLastAttemptDate(lastAttempt.getAttemptDate());
                
                testScores.add(scoreDTO);
            }
        }
        
        progressDTO.setTestScores(testScores);
        
        return progressDTO;
    }

    @Transactional(readOnly = true)
    public List<TestAttempt> getTestAttempts(Long testId, User user) {
        PracticeTest test = testRepository.findById(testId)
                .orElseThrow(() -> new ResourceNotFoundException("PracticeTest", "id", testId));
        
        return attemptRepository.findByUserAndTest(user, test);
    }

    @Transactional(readOnly = true)
    public TestAttempt getBestTestAttempt(Long testId, User user) {
        PracticeTest test = testRepository.findById(testId)
                .orElseThrow(() -> new ResourceNotFoundException("PracticeTest", "id", testId));
        
        List<TestAttempt> attempts = attemptRepository.findByUserAndTest(user, test);
        
        if (attempts.isEmpty()) {
            throw new ResourceNotFoundException("TestAttempt", "testId and userId", testId + ", " + user.getId());
        }
        
        return attempts.stream()
                .max(Comparator.comparing(TestAttempt::getScore))
                .orElse(attempts.get(0));
    }
}