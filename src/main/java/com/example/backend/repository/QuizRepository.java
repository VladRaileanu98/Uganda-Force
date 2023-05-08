package com.example.backend.repository;

import com.example.backend.model.Course;
import com.example.backend.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuizRepository extends JpaRepository<Quiz, Integer> {
    List<Quiz> getQuizzesByCourse(Course course);
    Quiz findQuizById(Integer id);
}