package com.example.backend.controller;

import com.example.backend.model.Grade;
import com.example.backend.model.User;
import com.example.backend.repository.GradeRepository;
import com.example.backend.repository.QuizRepository;
import com.example.backend.repository.UserRepository;
import com.example.backend.service.GradeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("grade")
public class GradeController {
    private final GradeService gradeService;

    @PostMapping("add/{quizId}/{userId}/{gradeValue}")
    public Grade createGrade(@PathVariable Integer quizId, @PathVariable Integer userId, @PathVariable Integer gradeValue){
        return gradeService.createGrade(quizId,userId,gradeValue);
    }

    @GetMapping("allGrades/{studentId}")
    public List<Grade> getAllGradesByStudentId(@PathVariable Integer studentId){
        return gradeService.getAllGradesByStudentId(studentId);
    }
}
