

package com.knowit.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.knowit.app.model.PracticeTest;
import com.knowit.app.model.TestAttempt;
import com.knowit.app.model.User;

@Repository
public interface TestAttemptRepository extends JpaRepository<TestAttempt, Long> {
    List<TestAttempt> findByUser(User user);
    List<TestAttempt> findByTest(PracticeTest test);
    List<TestAttempt> findByUserAndTest(User user, PracticeTest test);
}