package com.example.backend.service;

import com.example.backend.exception.NoChoiceException;
import com.example.backend.exception.NoQuestionException;
import com.example.backend.exception.ResourceNotFoundException;
import com.example.backend.model.Choice;
import com.example.backend.model.Question;
import com.example.backend.repository.ChoiceRepository;
import com.example.backend.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final ChoiceRepository choiceRepository;

    public List<Question> getAllQuestions(){
        return questionRepository.findAll();
    }

    public Integer getParentQuizId(@PathVariable Integer questionId){
        return questionRepository.getQuestionById(questionId).getParentQuizId();
    }

    public Question createQuestion(Question question){
        question.setNoOfChoices(0);
        return questionRepository.save(question);
    }

    public ResponseEntity<Question> getQuestionById(Integer id){
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Question not exist with id:" + id));

        return ResponseEntity.ok(question);
    }

    public List<Choice> getAllChoicesByQuestion(Integer questionId) {
        Question question = questionRepository.getQuestionById(questionId);
        return question.getChoiceList();
    }

    public ResponseEntity<Question> updateQuestion(Integer id, Question questionDetails){
        Question updateQuestion = questionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("QUestion doesnt exist:" + id));

        updateQuestion.setQuestion(questionDetails.getQuestion());
        updateQuestion.setScore(questionDetails.getScore());


        questionRepository.save(updateQuestion);

        return ResponseEntity.ok(updateQuestion);
    }

    public void addChoice(Integer choiceId, Integer questionId) throws NoQuestionException, NoChoiceException {
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
            optionalChoice.get().setParentQuestionId(questionId);
            choiceRepository.save(optionalChoice.get());
            questionRepository.save(optionalQuestion.get());
        }
    }

    public ResponseEntity<HttpStatus> deleteQuestion(Integer id){
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Question doesnt exist with id: "+id));

        questionRepository.delete(question);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
