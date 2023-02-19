package com.example.backend.controller;

import com.example.backend.model.Course;
import com.example.backend.repository.CourseRepository;
import com.example.backend.service.CourseService;
import com.example.backend.service.EmailService;
import com.example.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("course")
public class CourseController {
    private final CourseService courseService;
    private final UserService userService;
    private final EmailService emailService;
    private final CourseRepository courseRepository;

    @CrossOrigin(origins = {"*"})
    @RequestMapping(value = "/getAllCourses", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity<List<Course>> getAllCourses() {

        List<Course> courseList = courseService.getAllCourses();

        if(courseList.size() > 0){
            return new ResponseEntity<>(courseList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("file/download-file/{courseId}/{fileName}")
    public void downloadObject(@PathVariable Integer courseId, @PathVariable String fileName) {
        courseService.downloadObject(courseId, fileName, false);
    }

    @GetMapping("file/download-file/{courseId}/shared/{fileName}")
    public void downloadObject2(@PathVariable Integer courseId, @PathVariable String fileName) {
        courseService.downloadObject(courseId, fileName, true);
    }
}
