package com.example.backend.service;

import com.example.backend.exception.FoundDuplicateException;
import com.example.backend.exception.NoCourseException;
import com.example.backend.exception.NoUserException;
import com.example.backend.model.Course;
import com.example.backend.model.User;
import com.example.backend.repository.CourseRepository;
import com.example.backend.repository.RoleRepository;
import com.example.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.net.http.HttpClient;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final CourseRepository courseRepository;
    private final EmailService emailService;

    public List<User> getUsers(){
        return userRepository.findAll();
    }

    public User findUserById(Integer userId) {
        return userRepository.findUserById(userId);
    }

    public User findUserByEmail(String email){
        return userRepository.findUserByEmail(email);
    }
    public ResponseEntity<User> getUserByEmail(Map<String, String> headers) {
        User requestUser = findUserByEmail(headers.get("email"));
        if (requestUser != null) {
            return new ResponseEntity<>(requestUser, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public User createEmployee(User user) {
        if(userRepository.countByEmail(user.getEmail()) == 0){
            user.setRole(roleRepository.getRoleByName("employee"));
            return userRepository.save(user);
        } else{
            return null;
        }
    }

    public ResponseEntity<User> addUser(User user) {
        User newUser = createEmployee(user);
        if (newUser != null)
            return new ResponseEntity<>(newUser, HttpStatus.CREATED);
        else
            return new ResponseEntity<>(HttpStatus.FOUND);
    }

    public ResponseEntity<HttpClient> sendPassResetMail(Map<String, String> body) {
        String resetPasswordCode = UUID.randomUUID().toString();
        String email = body.get("email");
        User requestUser = findUserByEmail(body.get("email"));
        return emailService.sendForgotPasswordEmail(email, resetPasswordCode);
    }

    public User updateUserPasswordByEmail(User user){
        User userToUpdate = userRepository.findUserByEmail(user.getEmail());
        userToUpdate.setPassword(user.getPassword());
        userRepository.save(userToUpdate);
        return userToUpdate;
    }

    public void assignCourseToUser(Integer courseId, Integer userId) throws NoCourseException, NoUserException, FoundDuplicateException {
        Optional<Course> courseOptional = courseRepository.findById(courseId);
        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isEmpty())
            throw new NoUserException();
        else if(courseOptional.isEmpty()){
            throw new NoCourseException();
        }
        else {
            if (userOptional.get().getCourseList().contains(courseOptional.get()))
                throw new FoundDuplicateException();
            userOptional.get().getCourseList().add(courseOptional.get());
            userRepository.save(userOptional.get());
        }
    }
}
