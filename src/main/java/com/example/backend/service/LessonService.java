package com.example.backend.service;


import com.example.backend.model.Lesson;
import com.example.backend.repository.CourseRepository;
import com.example.backend.repository.LessonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LessonService {

    private final LessonRepository lessonRepository;
    private final CourseRepository courseRepository;

    public List<Lesson> getAllLessons(){
        return lessonRepository.findAll();
    }

    public Lesson getLessonById(Integer lessonId){
        return lessonRepository.getLessonById(lessonId);
    }

//    public List<Lesson> getAllLessonsByCourseId(@PathVariable Integer courseId){
//        return courseRepository.findById(courseId).get().getLessons();
//    }

    public Integer getParentCourseId(Integer lessonId){
        return lessonRepository.getLessonById(lessonId).getParentCourseId();
    }

    public Lesson createLesson(Lesson lesson){
        return lessonRepository.save(lesson);
    }

    public Lesson updateLesson(Integer lessonId,Lesson lesson){
        Lesson updatedLesson = lessonRepository.getLessonById(lessonId);
        updatedLesson.setName(lesson.getName());
        updatedLesson.setDescription(lesson.getDescription());
        lessonRepository.save(updatedLesson);
        return updatedLesson;
    }

    public boolean deleteLesson(Integer lessonId){
        lessonRepository.delete(lessonRepository.getLessonById(lessonId));
        return true;
    }
}
