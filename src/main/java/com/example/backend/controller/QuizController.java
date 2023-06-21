package com.example.backend.controller;

import com.example.backend.exception.*;
import com.example.backend.model.Grade;
import com.example.backend.model.Question;
import com.example.backend.model.Quiz;
import com.example.backend.repository.GradeRepository;
import com.example.backend.repository.QuestionRepository;
import com.example.backend.repository.QuizRepository;
import com.example.backend.service.CourseService;
import com.example.backend.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("/quiz")
public class QuizController {
    @Autowired
    private QuizService quizService;
    @GetMapping
    public List<Quiz> getAllQuizzes(){
        return quizService.getAllQuizzes();
    }

    @GetMapping("/questions/{quizId}")
    public List<Question> getAllQuestionsByQuiz(@PathVariable Integer quizId) throws NoQuizException {
        return quizService.getAllQuestionsByQuiz(quizId);
    }
    @GetMapping("/grades/{quizId}")
    public List<Grade> getAllGradesByQuiz(@PathVariable Integer quizId) throws NoQuizException {
        return quizService.getAllGradesByQuiz(quizId);
    }

    @GetMapping("/{id}")
    public Quiz getQuizById(@PathVariable Integer id){
        return quizService.getQuizById(id);
    }

    @GetMapping("/{quizId}/course")
    public Integer getParentCourseId(@PathVariable Integer quizId){
        return quizService.getParentCourseId(quizId);
    }
    
    @PostMapping("/create")
    public Quiz createQuiz(@RequestBody Quiz quiz){
        return quizService.createQuiz(quiz);
    }


    @PutMapping("/questions/add/{questionId}/{quizId}")
    public void addQuestion(@PathVariable Integer questionId, @PathVariable Integer quizId) throws NoQuizException, NoQuestionException {
        quizService.addQuestion(questionId,quizId);
    }

    @PutMapping("/grades/add/{gradeId}/{quizId}")
    public void addGrade(@PathVariable Integer gradeId, @PathVariable Integer quizId) throws NoQuizException, NoGradeException {
        quizService.addGrade(gradeId,quizId);
    }

    @PutMapping("{id}")
    public ResponseEntity<Quiz> updateQuiz(@PathVariable Integer id, @RequestBody Quiz quizDetails){
        return quizService.updateQuiz(id, quizDetails);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> deleteQuiz(@PathVariable Integer id){
        return quizService.deleteQuiz(id);
    }

}
