package com.example.backend.controller;

import com.example.backend.exception.ResourceNotFoundException;
import com.example.backend.model.Choice;
import com.example.backend.repository.ChoiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/choice")
public class ChoiceController {
    @Autowired
    private ChoiceRepository choiceRepository;

    @GetMapping
    public List<Choice> getAllChoices(){
        return choiceRepository.findAll();
    }
    // build create choice REST API
    @PostMapping("/create")
    public Choice createChoice(@RequestBody Choice choice){
        return choiceRepository.save(choice);
    }

    @GetMapping("{id}")
    public ResponseEntity<Choice> getChoiceById(@PathVariable Integer id){
        Choice choice = choiceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Choice not exist with id:" + id));

        return ResponseEntity.ok(choice);
    }

    // build update Choice REST API
    @PutMapping("{id}")
    public ResponseEntity<Choice> updateChoice(@PathVariable Integer id, @RequestBody Choice choiceDetails){
        Choice updateChoice = choiceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Choice doesnt exist:" + id));

        updateChoice.setAnswer(choiceDetails.getAnswer());
        updateChoice.setIsCorrect(choiceDetails.getIsCorrect());


        choiceRepository.save(updateChoice);

        return ResponseEntity.ok(updateChoice);
    }

    // build delete Choice REST API
    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> deleteChoice(@PathVariable Integer id){
        Choice choice = choiceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Choice doesnt exist with id: "+id));

        choiceRepository.delete(choice);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
