package com.example.backend.controller;

import com.example.backend.model.Message;
import com.example.backend.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("/message")
public class MessageController {
    public final MessageService messageService;


    @GetMapping("/showAll")
    public List<Message> getAllMessages(){
        return messageService.getAllMessages();
    }

    @GetMapping("/showById/{messageId}")
    public Message getMessageById(@PathVariable Integer messageId){
        return messageService.getMessageById(messageId);
    }

    @GetMapping("/{messageId}/sender")
    public Integer getSenderId(@PathVariable Integer messageId){
        return messageService.getSenderId(messageId);
    }
    @GetMapping("/{messageId}/receiver")
    public Integer getReceiverId(@PathVariable Integer messageId){
        return messageService.getReceiverId(messageId);
    }

    @PostMapping("/create")
    public Message createMessage(@RequestBody Message message){
        return messageService.createMessage(message);
    }

//    public Lesson updateLesson(Integer lessonId,Lesson lesson){
//        Lesson updatedLesson = messageRepository.getLessonById(lessonId);
//        updatedLesson.setName(lesson.getName());
//        updatedLesson.setDescription(lesson.getDescription());
//        updatedLesson.setQuizId(lesson.getQuizId());
//        updatedLesson.setVideoLink(lesson.getVideoLink());
//        messageRepository.save(updatedLesson);
//        return updatedLesson;
//    }

    @DeleteMapping("/delete/{messageId}")
    public ResponseEntity<Map<String,Boolean>> deleteMessage(@PathVariable Integer messageId){
        boolean deleted = false;
        deleted = messageService.deleteMessage(messageId);
        Map<String,Boolean> response = new HashMap<>();
        response.put("deleted", deleted);
        return ResponseEntity.ok(response);
    }
}
