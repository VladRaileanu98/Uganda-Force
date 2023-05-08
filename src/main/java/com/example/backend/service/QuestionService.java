package com.example.backend.service;

import com.example.backend.exception.NoQuestionException;
import com.example.backend.model.Choice;
import com.example.backend.model.Question;
import com.example.backend.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;

    public List<Choice> getAllChoicesByQuestion(Integer questionId) throws NoQuestionException {
        Optional<Question> optionalQuestion = questionRepository.findById(questionId);

        if(!optionalQuestion.isPresent()) throw new NoQuestionException();
        else{
            return optionalQuestion.get().getChoiceList();
        }
    }
}
