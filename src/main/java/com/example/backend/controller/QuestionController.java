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
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("/question")
public class QuestionController {
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private ChoiceRepository choiceRepository;
    @Autowired
    private QuestionService questionService;


    @GetMapping
    public List<Question> getAllQuestions(){
        return questionRepository.findAll();
    }

    @GetMapping("/choices/{questionId}")
    public List<Choice> getAllChoicesByQuestion(@PathVariable Integer questionId){
        return questionService.getAllChoicesByQuestion(questionId);
    }


    // build create question REST API
    @PostMapping("/create")
    public Question createQuestion(@RequestBody Question question){
        question.setNoOfChoices(0);
        return questionRepository.save(question);
    }

    // buikld get question by id REST API
    @GetMapping("{id}")
    public ResponseEntity<Question> getQuestionById(@PathVariable Integer id){
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Question not exist with id:" + id));

        return ResponseEntity.ok(question);
    }

    @PutMapping("/choices/add/{choiceId}/{questionId}")
    public void addChoice(@PathVariable Integer choiceId, @PathVariable Integer questionId) throws NoQuestionException, NoChoiceException {
        Optional<Question> optionalQuestion = questionRepository.findById(questionId);
        Optional<Choice> optionalChoice = choiceRepository.findById(choiceId);
        if(!optionalQuestion.isPresent())
            throw new NoQuestionException();
        else if(!optionalChoice.isPresent()){
            throw new NoChoiceException();
        }
        else{
            optionalQuestion.get().getChoiceList().add(optionalChoice.get());
            optionalQuestion.get().incrementNoOfChoices();
            optionalChoice.get().setQuestion(optionalQuestion.get());
            choiceRepository.save(optionalChoice.get());
            questionRepository.save(optionalQuestion.get());
        }
    }

    // build update Question REST API
    @PutMapping("{id}")
    public ResponseEntity<Question> updateQuestion(@PathVariable Integer id, @RequestBody Question questionDetails){
        Question updateQuestion = questionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("QUestion doesnt exist:" + id));

        updateQuestion.setQuestion(questionDetails.getQuestion());
        updateQuestion.setScore(questionDetails.getScore());


        questionRepository.save(updateQuestion);

        return ResponseEntity.ok(updateQuestion);
    }

    // build delete question REST API
    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> deleteQuestion(@PathVariable Integer id){
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Question doesnt exist with id: "+id));

        //question.getQuiz().decrementNoOfQuestions();
        questionRepository.delete(question);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
