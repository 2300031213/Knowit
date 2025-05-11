
package com.knowit.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.knowit.app.model.Course;
import com.knowit.app.model.PracticeTest;

@Repository
public interface PracticeTestRepository extends JpaRepository<PracticeTest, Long> {
    List<PracticeTest> findByCourse(Course course);
}