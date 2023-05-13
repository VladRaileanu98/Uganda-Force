package com.example.backend.service;

import com.example.backend.exception.NoGradeException;
import com.example.backend.exception.NoQuestionException;
import com.example.backend.exception.NoQuizException;
import com.example.backend.exception.ResourceNotFoundException;
import com.example.backend.model.Grade;
import com.example.backend.model.Question;
import com.example.backend.model.Quiz;
import com.example.backend.repository.GradeRepository;
import com.example.backend.repository.QuestionRepository;
import com.example.backend.repository.QuizRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuizService {
    private final QuizRepository quizRepository;
    private final QuestionRepository questionRepository;
    private final GradeRepository gradeRepository;

    public List<Quiz> getAllQuizzes(){
        return quizRepository.findAll();
    }

    public List<Question> getAllQuestionsByQuiz(Integer quizId) throws NoQuizException{
        Optional<Quiz> optionalQuiz = quizRepository.findById(quizId);

        if(!optionalQuiz.isPresent()) throw new NoQuizException();
        else{
            return optionalQuiz.get().getQuestionList();
        }
    }

    public Quiz getQuizById(Integer id){
        Quiz quiz = quizRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Quiz not exist with id:" + id));

        return quiz;
    }

    public Integer getParentCourseId(Integer quizId){
        return quizRepository.findQuizById(quizId).getParentCourseId();
    }

    public List<Grade> getAllGradesByQuiz(Integer quizId) throws NoQuizException{
        Optional<Quiz> optionalQuiz = quizRepository.findById(quizId);

        if(!optionalQuiz.isPresent()) throw new NoQuizException();
        else{
            return optionalQuiz.get().getGradeList();
        }
    }

    public Quiz createQuiz(@RequestBody Quiz quiz){
        quiz.setNoOfQuestions(0);
        return quizRepository.save(quiz);
    }

    public void addQuestion(Integer questionId, Integer quizId) throws NoQuizException, NoQuestionException {
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
            optionalQuestion.get().setParentQuizId(quizId);
            questionRepository.save(optionalQuestion.get());
            quizRepository.save(optionalQuiz.get());
        }
    }

    public void addGrade(Integer gradeId, Integer quizId) throws NoQuizException, NoGradeException {
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

    public ResponseEntity<Quiz> updateQuiz(Integer id,Quiz quizDetails){
        Quiz updateQuiz = quizRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Quiz doesnt exist:" + id));

        updateQuiz.setIsVisible(quizDetails.getIsVisible());
        updateQuiz.setTimeLimit(quizDetails.getTimeLimit());
        updateQuiz.setDeadline(quizDetails.getDeadline());

        quizRepository.save(updateQuiz);

        return ResponseEntity.ok(updateQuiz);
    }

    public ResponseEntity<HttpStatus> deleteQuiz(Integer id){
        Quiz quiz = quizRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Quiz doesnt exist with id: "+id));

        quizRepository.delete(quiz);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
