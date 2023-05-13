package com.example.backend.controller;

import com.example.backend.exception.NoChoiceException;
import com.example.backend.exception.NoQuestionException;
import com.example.backend.exception.ResourceNotFoundException;
import com.example.backend.model.Choice;
import com.example.backend.model.Question;
import com.example.backend.repository.ChoiceRepository;
import com.example.backend.repository.QuestionRepository;
import com.example.backend.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("/question")
public class QuestionController {
    @Autowired
    private QuestionService questionService;

    @GetMapping
    public List<Question> getAllQuestions(){
        return questionService.getAllQuestions();
    }

    @GetMapping("/choices/{questionId}")
    public List<Choice> getAllChoicesByQuestion(@PathVariable Integer questionId){
        return questionService.getAllChoicesByQuestion(questionId);
    }

    @PostMapping("/create")
    public Question createQuestion(@RequestBody Question question){
        return questionService.createQuestion(question);
    }

    // buikld get question by id REST API
    @GetMapping("{id}")
    public ResponseEntity<Question> getQuestionById(@PathVariable Integer id){
        return questionService.getQuestionById(id);
    }

    @GetMapping("/{questionId}/quiz")
    public Integer getParentQuizId(@PathVariable Integer questionId){
        return questionService.getParentQuizId(questionId);
    }

    @PutMapping("/choices/add/{choiceId}/{questionId}")
    public void addChoice(@PathVariable Integer choiceId, @PathVariable Integer questionId) throws NoQuestionException, NoChoiceException {
        questionService.addChoice(choiceId,questionId);
    }

    // build update Question REST API
    @PutMapping("{id}")
    public ResponseEntity<Question> updateQuestion(@PathVariable Integer id, @RequestBody Question questionDetails){
        return questionService.updateQuestion(id, questionDetails);
    }

    // build delete question REST API
    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> deleteQuestion(@PathVariable Integer id){
        return questionService.deleteQuestion(id);
    }

}
