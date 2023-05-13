package com.example.backend.controller;

import com.example.backend.exception.ResourceNotFoundException;
import com.example.backend.model.Choice;
import com.example.backend.repository.ChoiceRepository;
import com.example.backend.service.ChoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("/choice")
public class ChoiceController {
    private final ChoiceService choiceService;

    @GetMapping
    public List<Choice> getAllChoices(){
        return choiceService.getAllChoices();
    }

    @GetMapping("{id}")
    public ResponseEntity<Choice> getChoiceById(@PathVariable Integer id){
        return choiceService.getChoiceById(id);
    }

    @GetMapping("/{choiceId}/question")
    public Integer getParentQuestionId(@PathVariable Integer choiceId){
        return choiceService.getParentQuestionId(choiceId);
    }
    @PostMapping("/create")
    public Choice createChoice(@RequestBody Choice choice){
        return choiceService.createChoice(choice);
    }

    @PutMapping("{id}")
    public ResponseEntity<Choice> updateChoice(@PathVariable Integer id, @RequestBody Choice choiceDetails){
        return choiceService.updateChoice(id,choiceDetails);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> deleteChoice(@PathVariable Integer id){
        return choiceService.deleteChoice(id);
    }

}
