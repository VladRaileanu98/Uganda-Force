package com.example.backend.controller;

import com.example.backend.model.Course;
import com.example.backend.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/course")
public class CourseController {
    private final CourseService courseService;

    @GetMapping("/showById/{courseId}")
    public ResponseEntity<Course> getCourseById(@PathVariable Integer courseId){
        return courseService.getCourseById(courseId);
    }

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

    @GetMapping("file/download-file/{courseId}/{fileName}")
    public void downloadObject(@PathVariable Integer courseId, @PathVariable String fileName) {
        courseService.downloadObject(courseId, fileName, false);
    }

    @GetMapping("file/download-file/{courseId}/shared/{fileName}")
    public void downloadObject2(@PathVariable Integer courseId, @PathVariable String fileName) {
        courseService.downloadObject(courseId, fileName, true);
    }

    @PostMapping("create")
    public Course createCourse(@RequestBody Course course){
        return courseService.addCourse(course);
    }
}
