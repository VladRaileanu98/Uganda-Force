package com.example.backend.controller;

import com.example.backend.model.Grade;
import com.example.backend.model.User;
import com.example.backend.repository.GradeRepository;
import com.example.backend.repository.QuizRepository;
import com.example.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("grade")
public class GradeController {
    private final GradeRepository gradeRepository;
    private final UserRepository userRepository;
   // private final GradeMapper gradeMapper;
    private final QuizRepository quizRepository;

    @PostMapping("add/{quizId}/{userId}/{gradeValue}")
    public Grade createGrade(@PathVariable Integer quizId, @PathVariable Integer userId, @PathVariable Integer gradeValue){
        Grade grade = new Grade();
        grade.setUser(userRepository.findUserById(userId));
        grade.setQuiz(quizRepository.findQuizById(quizId));
        grade.setGrade(gradeValue);
        return gradeRepository.save(grade);
    }

    @GetMapping("allGrades/{studentId}")
    public List<Grade> getAllGradesByStudentId(@PathVariable Integer studentId){
        User user = userRepository.findUserById(studentId);
        List<Grade> grades=gradeRepository.getAllByUser(user);

        //System.out.println(gradeMapper.toListGradeDto(grades));
        return grades;
    }
}
