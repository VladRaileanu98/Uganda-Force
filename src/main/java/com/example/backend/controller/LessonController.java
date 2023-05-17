package com.example.backend.controller;


import com.example.backend.model.Lesson;
import com.example.backend.service.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("/lesson")
public class LessonController {

    private final LessonService lessonService;

    @GetMapping("/showAll")
    public List<Lesson> getAllLessons(){
        return lessonService.getAllLessons();
    }

    @GetMapping("/showById/{lessonId}")
    public Lesson getLessonById(@PathVariable Integer lessonId){
        return lessonService.getLessonById(lessonId);
    }

    @GetMapping("/showAll/{courseId}")
    public List<Lesson> getAllLessonsByCourseId(@PathVariable Integer courseId){
        return lessonService.getAllLessonsByCourseId(courseId);
    }

    @GetMapping("/{lessonId}/course")
    private Integer getParentCourseId(@PathVariable Integer lessonId){
        return lessonService.getParentCourseId(lessonId);
    }

    @PostMapping("/create")
    public Lesson createLesson(@RequestBody Lesson lesson){
        return lessonService.createLesson(lesson);
    }

    @PutMapping("/update/{lessonId}")
    public ResponseEntity<Lesson> updateLesson(@PathVariable Integer lessonId, @RequestBody Lesson lesson){
        lesson = lessonService.updateLesson(lessonId,lesson);
        return ResponseEntity.ok(lesson);
    }

    @DeleteMapping("/delete/{lessonId}")
    public ResponseEntity<Map<String,Boolean>> deleteLesson(@PathVariable Integer lessonId){
        boolean deleted = false;
        deleted = lessonService.deleteLesson(lessonId);
        Map<String,Boolean> response = new HashMap<>();
        response.put("deleted", deleted);
        return ResponseEntity.ok(response);
    }
}
