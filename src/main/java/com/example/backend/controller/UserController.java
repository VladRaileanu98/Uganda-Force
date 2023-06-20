package com.example.backend.controller;

import com.example.backend.exception.FoundDuplicateException;
import com.example.backend.exception.NoCourseException;
import com.example.backend.exception.NoUserException;
import com.example.backend.model.Course;
import com.example.backend.model.User;
import com.example.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.hibernate.HibernateException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @GetMapping( "/showAll")
    public List<User> showUsers(){
        return userService.getUsers();
    }

    @GetMapping("/showAllCourses/{userId}")
    public List<Course> showUserCourses(@PathVariable Integer userId){
        return userService.getUserCourses(userId);
    }

    @GetMapping( "/showById/{userId}")
    public User findUserById(@PathVariable Integer userId) {
        return userService.findUserById(userId);
    }

    @RequestMapping(value = "/checkEmail", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity<User> getUserByEmail(@RequestHeader Map<String, String> headers) {
        return userService.getUserByEmail(headers);
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @PostMapping(value = "/signUp")
    public ResponseEntity<User> addUser(@RequestBody User user) {
        return userService.addUser(user);
    }


    @PutMapping( "/updatePassword")
    public User updateUserPasswordByEmail(@Valid @RequestBody User theUser) {
        try {
            return userService.updateUserPasswordByEmail(theUser);

        }catch (HibernateException e){
            return null;
        }
    }

    @PutMapping("/add-course/{courseId}/{userId}")
    public ResponseEntity<String> assignCourseToUser(@PathVariable Integer courseId, @PathVariable  Integer userId) throws NoCourseException, NoUserException, FoundDuplicateException {
        userService.assignCourseToUser(courseId,userId);
        return ResponseEntity.ok("added course no." + courseId + " to user with id: " + userId);
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Integer id, @RequestBody User user){
        user = userService.updateUser(id, user);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String,Boolean>> deleteUser(@PathVariable Integer id){
        boolean deleted = false;
        deleted = userService.deleteUser(id);
        Map<String,Boolean> response = new HashMap<>();
        response.put("deleted", deleted);
        return ResponseEntity.ok(response);
    }
}




