package com.example.backend.service;
import com.example.backend.model.Role;
import com.example.backend.model.User;
import com.example.backend.repository.RoleRepository;
import com.example.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.net.http.HttpClient;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmailService {


    private final JavaMailSender emailSender;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public void sendSimpleMessage(
            String to, String subject, String text) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("grupa8elearning@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);

    }

    public ResponseEntity<HttpClient> sendForgotPasswordEmail(String email, String resetPasswordCode) {

        sendSimpleMessage(email,
                "Change password",
                "Access the following link to change your password:\n" +
                        "http://localhost:3000/ParolaNoua?code=" + resetPasswordCode + "&email=" + email
        );

        return new ResponseEntity<>(HttpStatus.OK);
    }
    public ResponseEntity<String> sendWhenACourseIsAdded( String courseName) {
        Role role = roleRepository.getRoleByName("student") ;
        List<User> students = userRepository.findAllByRole(role) ;
        for(User student : students) {

            sendSimpleMessage(student.getEmail(),
                    "new course",
                    "A new course is waiting for you : " + courseName
            );
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
    public ResponseEntity<String> sendWhenAQuizIsAvailable(String studentEmail, String newTakenQuiz) {
        sendSimpleMessage(studentEmail,
                "Quiz Available",
                "A new quiz is available\n" +
                        "http://localhost:3000/QuizAvailable?code=" + newTakenQuiz + "&email=" + studentEmail
        );

        return new ResponseEntity<>(HttpStatus.OK);
    }
}