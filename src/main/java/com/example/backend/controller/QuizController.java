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
//@RequestMapping("quiz")
@RequestMapping("/quiz")
public class QuizController {
    @Autowired
    private QuizRepository quizRepository;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private GradeRepository gradeRepository;

    @Autowired
    private QuizService quizService;
    @GetMapping
    public List<Quiz> getAllQuizzes(){
        return quizRepository.findAll();
    }

    //******* NU VREA SAM EARGA IN FRONTEND
    @GetMapping("questions/{quizId}")
    public List<Question> getAllQuestionsByQuiz(@PathVariable Integer quizId) throws NoQuizException {
        return quizService.getAllQuestionsByQuiz(quizId);
    }
    @GetMapping("grades/{quizId}")
    public List<Grade> getAllGradesByQuiz(@PathVariable Integer quizId) throws NoQuizException {
        return quizService.getAllGradesByQuiz(quizId);
    }

    @PutMapping("/increment/{id}")
    public void incrementNoQuestionQuiz(@PathVariable Integer id){
        Optional<Quiz> optionalQuiz = quizRepository.findById(id);
        optionalQuiz.get().incrementNoOfQuestions();
        quizRepository.save(optionalQuiz.get());
    }

    @GetMapping("{id}")
    public Quiz getQuizById(@PathVariable Integer id){
        Quiz quiz = quizRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Quiz not exist with id:" + id));

        return quiz;
    }

    @PostMapping("/create")
    public Quiz createQuiz(@RequestBody Quiz quiz){
        quiz.setNoOfQuestions(0);
        return quizRepository.save(quiz);
    }


    @PutMapping("/questions/add/{questionId}/{quizId}")
    public void addQuestion(@PathVariable Integer questionId, @PathVariable Integer quizId) throws NoQuizException, NoQuestionException {
        Optional<Quiz> optionalQuiz = quizRepository.findById(quizId);
        Optional<Question> optionalQuestion = questionRepository.findById(questionId);
        if(!optionalQuiz.isPresent())
            throw new NoQuizException();
        else if(!optionalQuestion.isPresent()){
            throw new NoQuestionException();
        }
        else{
            optionalQuiz.get().getQuestionList().add(optionalQuestion.get());
            optionalQuiz.get().incrementNoOfQuestions();
            quizRepository.save(optionalQuiz.get());
        }
    }

    @PutMapping("/grades/add/{gradeId}/{quizId}")
    public void addGrade(@PathVariable Integer gradeId, @PathVariable Integer quizId) throws NoQuizException, NoGradeException {
        Optional<Quiz> optionalQuiz = quizRepository.findById(quizId);
        Optional<Grade> optionalGrade = gradeRepository.findById(gradeId);
        if(!optionalQuiz.isPresent())
            throw new NoQuizException();
        else if(!optionalGrade.isPresent()){
            throw new NoGradeException();
        }
        else{
            optionalQuiz.get().getGradeList().add(optionalGrade.get());
            optionalQuiz.get().incrementNoOfQuestions();
            quizRepository.save(optionalQuiz.get());
        }
    }
    // build update Quiz REST API
    @PutMapping("{id}")
    public ResponseEntity<Quiz> updateQuiz(@PathVariable Integer id, @RequestBody Quiz quizDetails){
        Quiz updateQuiz = quizRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Quiz doesnt exist:" + id));

        updateQuiz.setIsVisible(quizDetails.getIsVisible());
        updateQuiz.setTimeLimit(quizDetails.getTimeLimit());
        updateQuiz.setDeadline(quizDetails.getDeadline());

        quizRepository.save(updateQuiz);

        return ResponseEntity.ok(updateQuiz);
    }

    // build delete Quiz REST API
    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> deleteQuiz(@PathVariable Integer id){
        Quiz quiz = quizRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Quiz doesnt exist with id: "+id));

        quizRepository.delete(quiz);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
