package com.example.backend.service;

import com.example.backend.model.Message;
import com.example.backend.repository.MessageRepository;
import com.example.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    public List<Message> getAllMessages(){
        return messageRepository.findAll();
    }

    public Message getMessageById(Integer messageId){
        return messageRepository.getMessageById(messageId);
    }

    public Integer getSenderId(Integer messageId){
        return messageRepository.getMessageById(messageId).getSenderId();
    }
    public Integer getReceiverId(Integer messageId){
        return messageRepository.getMessageById(messageId).getReceiverId();
    }

    public Message createMessage(Message message){
        userRepository.getUserById(message.getSenderId()).getSentMessages().add(message);
        userRepository.getUserById(message.getReceiverId()).getReceivedMessages().add(message);
        return messageRepository.save(message);
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

    public boolean deleteMessage(Integer messageId){
        messageRepository.delete(messageRepository.getMessageById(messageId));
        return true;
    }
}
