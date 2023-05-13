package com.example.backend.service;

import com.example.backend.exception.FoundDuplicateException;
import com.example.backend.exception.NoCourseException;
import com.example.backend.exception.NoQuizException;
import com.example.backend.exception.NoUserException;
import com.example.backend.model.*;
import com.example.backend.repository.*;
import com.google.cloud.storage.testing.RemoteStorageHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.storage.*;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;
    private final QuizRepository quizRepository;

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public List<Course> getAllCoursesByUserId(Integer userId){
        List<User> userList = new ArrayList<>();

        return null;
    }

    public List<Quiz> getAllQuizzes(Integer courseId){
        Course course = courseRepository.getCourseById(courseId);
        return course.getQuizList();
    }

    public Course getCourseById(Integer courseId){
        Course course= courseRepository.getCourseById(courseId);

        return course;
    }


    public Course addCourse(Course course) {
        return courseRepository.save(course);
    }

    public Course updateCourse(Integer id, Course course){
        Course updatedCourse = courseRepository.getCourseById(id);
        updatedCourse.setName(course.getName());
        updatedCourse.setDescription(course.getDescription());
        updatedCourse.setEmbedLink(course.getEmbedLink());
        courseRepository.save(updatedCourse);
        return updatedCourse;
    }

    public void assignQuizToCourse(Integer quizId, Integer courseId) throws NoCourseException, NoQuizException, FoundDuplicateException {
        Optional<Quiz> quizOptional = quizRepository.findById(quizId);
        Optional<Course> courseOptional = courseRepository.findById(courseId);
        if(courseOptional.isEmpty())
            throw new NoCourseException();
        else if(quizOptional.isEmpty()){
            throw new NoQuizException();
        }
        else {
            if (courseOptional.get().getQuizList().contains(quizOptional.get()))
                throw new FoundDuplicateException();
            courseOptional.get().getQuizList().add(quizOptional.get());
            quizOptional.get().setParentCourseId(courseId);
            quizRepository.save(quizOptional.get());
            courseRepository.save(courseOptional.get());
        }
    }

    public boolean deleteCourse(Integer id){
        Course course = courseRepository.getCourseById(id);
        courseRepository.delete(course);
        return true;
    }

}
