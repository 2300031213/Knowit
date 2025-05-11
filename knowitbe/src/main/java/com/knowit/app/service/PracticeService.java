

package com.knowit.app.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.knowit.app.dto.TestAttemptRequest;
import com.knowit.app.exception.ResourceNotFoundException;
import com.knowit.app.model.Course;
import com.knowit.app.model.PracticeTest;
import com.knowit.app.model.TestAttempt;
import com.knowit.app.model.TestQuestion;
import com.knowit.app.model.User;
import com.knowit.app.repository.CourseRepository;
import com.knowit.app.repository.PracticeTestRepository;
import com.knowit.app.repository.TestAttemptRepository;
import com.knowit.app.repository.TestQuestionRepository;

@Service
public class PracticeService {

    @Autowired
    private PracticeTestRepository testRepository;

    @Autowired
    private TestQuestionRepository questionRepository;

    @Autowired
    private TestAttemptRepository attemptRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Transactional(readOnly = true)
    public List<PracticeTest> getTestsByCourseId(Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course", "id", courseId));
        return testRepository.findByCourse(course);
    }

    @Transactional(readOnly = true)
    public PracticeTest getTestById(Long testId) {
        return testRepository.findById(testId)
                .orElseThrow(() -> new ResourceNotFoundException("PracticeTest", "id", testId));
    }

    @Transactional(readOnly = true)
    public List<TestQuestion> getQuestionsByTestId(Long testId) {
        PracticeTest test = getTestById(testId);
        return questionRepository.findByTest(test);
    }

    @Transactional
    public TestAttempt submitTestAttempt(Long testId, TestAttemptRequest attemptRequest, User user) {
        PracticeTest test = getTestById(testId);
        List<TestQuestion> questions = questionRepository.findByTest(test);

        // Create map of question ID to correct answer for quick lookup
        Map<Long, String> correctAnswers = questions.stream()
                .collect(Collectors.toMap(TestQuestion::getId, TestQuestion::getCorrectAnswer));

        // Calculate score
        int totalQuestions = questions.size();
        int correctCount = 0;
        Map<Long, String> userAnswers = attemptRequest.getAnswers();

        for (Map.Entry<Long, String> entry : userAnswers.entrySet()) {
            Long questionId = entry.getKey();
            String userAnswer = entry.getValue();
            
            if (correctAnswers.containsKey(questionId) && 
                correctAnswers.get(questionId).equalsIgnoreCase(userAnswer)) {
                correctCount++;
            }
        }

        // Calculate percentage score (out of 100)
        BigDecimal scorePercentage = BigDecimal.ZERO;
        if (totalQuestions > 0) {
            scorePercentage = new BigDecimal(correctCount)
                    .multiply(new BigDecimal(100))
                    .divide(new BigDecimal(totalQuestions), 2, RoundingMode.HALF_UP);
        }

        // Create and save the attempt
        TestAttempt attempt = TestAttempt.builder()
                .user(user)
                .test(test)
                .score(scorePercentage)
                .totalQuestions(totalQuestions)
                .correctAnswers(correctCount)
                .build();

        return attemptRepository.save(attempt);
    }

    @Transactional(readOnly = true)
    public List<TestAttempt> getAttemptsByUser(User user) {
        return attemptRepository.findByUser(user);
    }

    @Transactional(readOnly = true)
    public List<TestAttempt> getAttemptsByUserAndTest(User user, Long testId) {
        PracticeTest test = getTestById(testId);
        return attemptRepository.findByUserAndTest(user, test);
    }
}