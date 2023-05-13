package com.example.backend.service;

import com.example.backend.exception.ResourceNotFoundException;
import com.example.backend.model.Choice;
import com.example.backend.repository.ChoiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChoiceService {
    private final ChoiceRepository choiceRepository;

    public List<Choice> getAllChoices(){
        return choiceRepository.findAll();
    }

    public ResponseEntity<Choice> getChoiceById(Integer id){
        Choice choice = choiceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Choice not exist with id:" + id));

        return ResponseEntity.ok(choice);
    }

    public Choice createChoice(Choice choice){
        return choiceRepository.save(choice);
    }

    public ResponseEntity<Choice> updateChoice(Integer id, Choice choiceDetails){
        Choice updateChoice = choiceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Choice doesnt exist:" + id));

        updateChoice.setAnswer(choiceDetails.getAnswer());
        updateChoice.setIsCorrect(choiceDetails.getIsCorrect());


        choiceRepository.save(updateChoice);

        return ResponseEntity.ok(updateChoice);
    }

    public ResponseEntity<HttpStatus> deleteChoice(Integer id){
        Choice choice = choiceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Choice doesnt exist with id: "+id));

        choiceRepository.delete(choice);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

