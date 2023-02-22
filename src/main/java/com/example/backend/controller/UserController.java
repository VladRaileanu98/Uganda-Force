package com.example.backend.controller;

import com.example.backend.exception.FoundDuplicateException;
import com.example.backend.exception.NoCourseException;
import com.example.backend.exception.NoRoleException;
import com.example.backend.exception.NoUserException;
import com.example.backend.model.Course;
import com.example.backend.model.Role;
import com.example.backend.model.User;
import com.example.backend.repository.CourseRepository;
import com.example.backend.repository.UserRepository;
import com.example.backend.service.EmailService;
import com.example.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.hibernate.HibernateException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.http.HttpClient;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @RequestMapping(value = "/showAll", method = RequestMethod.GET)
    public List<User> showUsers(){
        return userService.getUsers();
    }

    @RequestMapping(value = "/showById/{userId}", method = RequestMethod.GET)
    public User findUserById(@PathVariable Integer userId) {
        return userService.findUserById(userId);
    }

    @RequestMapping(value = "/checkEmail", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity<User> getUserByEmail(@RequestHeader Map<String, String> headers) {
        return userService.getUserByEmail(headers);
    }

    @RequestMapping(value = "/signUp", method = RequestMethod.POST)
    public ResponseEntity<User> addUser(@RequestBody User user) {
        return userService.addUser(user);
    }

    @RequestMapping(value = "/sendPassResetMail", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity<HttpClient> sendPassResetMail(@RequestBody Map<String, String> body) {
        return userService.sendPassResetMail(body);
    }

    @RequestMapping(value = "/updatePassword", method = RequestMethod.PUT)
    public User updateUserPasswordByEmail(@Valid @RequestBody User theUser) {
        try {
            return userService.updateUserPasswordByEmail(theUser);

        }catch (HibernateException e){
            return null;
        }
    }

    @PutMapping("/add/{courseId}/{userId}")
    public void assignCourseToUser(@PathVariable Integer courseId,@PathVariable  Integer userId) throws NoCourseException, NoUserException, FoundDuplicateException {
        userService.assignCourseToUser(courseId,userId);
    }
}




