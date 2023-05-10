package com.example.backend.controller;

import com.example.backend.exception.FoundDuplicateException;
import com.example.backend.exception.NoCourseException;
import com.example.backend.exception.NoQuizException;
import com.example.backend.model.Course;
import com.example.backend.model.Quiz;
import com.example.backend.model.User;
import com.example.backend.repository.CourseRepository;
import com.example.backend.repository.QuizRepository;
import com.example.backend.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("/course")
public class CourseController {
    private final CourseService courseService;
    private final CourseRepository courseRepository;
    private final QuizRepository quizRepository;

    @RequestMapping(value = "/showAll", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity<List<Course>> getAllCourses() {

        List<Course> courseList = courseService.getAllCourses();

        if(courseList.size() > 0){
            return new ResponseEntity<>(courseList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/showAll/{userId}")
    public List<Course> getAllCoursesByUserId(@PathVariable Integer userId){
        return courseService.getAllCoursesByUserId(userId);
    }

    @GetMapping("/showById/{courseId}")
    public Course getCourseById(@PathVariable Integer courseId){
        return courseService.getCourseById(courseId);
    }

    @CrossOrigin(origins = {"*"})
    @GetMapping("/quizzes/{courseId}")
    public List<Quiz> getAllQuizzes(@PathVariable Integer courseId){
        Course course = courseRepository.getCourseById(courseId);
        return course.getQuizList();
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Course createCourse(@RequestBody Course course){
        return courseService.addCourse(course);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Course> updateCourse(@PathVariable Integer id, @RequestBody Course course){
        course = courseService.updateCourse(id, course);
        return ResponseEntity.ok(course);
    }

    @PutMapping("/add/{quizId}/{courseId}")
    public ResponseEntity<String> assignQuizToCourse(@PathVariable Integer quizId, @PathVariable  Integer courseId) throws NoCourseException, NoQuizException, FoundDuplicateException {
        courseService.assignQuizToCourse(quizId,courseId);
        return ResponseEntity.ok("added quiz no." + quizId + " to course with id: " + courseId);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String,Boolean>> deleteCourse(@PathVariable Integer id){
        boolean deleted = false;
        deleted = courseService.deleteCourse(id);
        Map<String,Boolean> response = new HashMap<>();
        response.put("deleted", deleted);
        return ResponseEntity.ok(response);
    }

}
