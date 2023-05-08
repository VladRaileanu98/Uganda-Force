package com.example.backend.service;

import com.example.backend.exception.NoQuizException;
import com.example.backend.model.Grade;
import com.example.backend.model.Question;
import com.example.backend.model.Quiz;
import com.example.backend.repository.QuizRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuizService {
    private final QuizRepository quizRepository;

    public List<Question> getAllQuestionsByQuiz(Integer quizId) throws NoQuizException{
        Optional<Quiz> optionalQuiz = quizRepository.findById(quizId);

        if(!optionalQuiz.isPresent()) throw new NoQuizException();
        else{
            return optionalQuiz.get().getQuestionList();
        }
    }

    public List<Grade> getAllGradesByQuiz(Integer quizId) throws NoQuizException{
        Optional<Quiz> optionalQuiz = quizRepository.findById(quizId);

        if(!optionalQuiz.isPresent()) throw new NoQuizException();
        else{
            return optionalQuiz.get().getGradeList();
        }
    }

}
