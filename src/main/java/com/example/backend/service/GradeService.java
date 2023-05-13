package com.example.backend.service;

import com.example.backend.model.Grade;
import com.example.backend.model.User;
import com.example.backend.repository.GradeRepository;
import com.example.backend.repository.QuizRepository;
import com.example.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GradeService {
    private final GradeRepository gradeRepository;
    private final UserRepository userRepository;
    private final QuizRepository quizRepository;

    public Grade createGrade(Integer quizId, Integer userId, Integer gradeValue){
        Grade grade = new Grade();
        grade.setUser(userRepository.findUserById(userId));
        grade.setQuiz(quizRepository.findQuizById(quizId));
        grade.setGrade(gradeValue);
        return gradeRepository.save(grade);
    }

    public List<Grade> getAllGradesByStudentId(Integer studentId){
        User user = userRepository.findUserById(studentId);
        List<Grade> grades=gradeRepository.getAllByUser(user);
        return grades;
    }

}
