package com.example.backend.service;

import com.example.backend.exception.FoundDuplicateException;
import com.example.backend.exception.NoCourseException;
import com.example.backend.exception.NoLessonException;
import com.example.backend.exception.NoQuizException;
import com.example.backend.model.*;
import com.example.backend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;
    private final QuizRepository quizRepository;
    private final LessonRepository lessonRepository;
    private final UserRepository userRepository;

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public List<Course> getAllCoursesByUserId(Integer userId){
        return userRepository.findById(userId).get().getCourseList();
    }

    public List<Quiz> getAllQuizzes(Integer courseId){
        Course course = courseRepository.getCourseById(courseId);
        return course.getQuizList();
    }

    public List<Lesson> getAllLessons(Integer courseId){
        Course course = courseRepository.getCourseById(courseId);
        return course.getLessonList();
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
        updatedCourse.setImageLink(course.getImageLink());
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

    public void assignLessonToCourse(Integer lessonId, Integer courseId) throws NoCourseException, NoLessonException, FoundDuplicateException {
        Optional<Lesson> lessonOptional = lessonRepository.findById(lessonId);
        Optional<Course> courseOptional = courseRepository.findById(courseId);
        if(courseOptional.isEmpty())
            throw new NoCourseException();
        else if(lessonOptional.isEmpty()){
            throw new NoLessonException();
        }
        else {
            if (courseOptional.get().getLessonList().contains(lessonOptional.get()))
                throw new FoundDuplicateException();
            courseOptional.get().getLessonList().add(lessonOptional.get());
            lessonOptional.get().setParentCourseId(courseId);
            lessonRepository.save(lessonOptional.get());
            courseRepository.save(courseOptional.get());
        }
    }

    public boolean deleteCourse(Integer id){
        Course course = courseRepository.getCourseById(id);
        courseRepository.delete(course);
        return true;
    }

}
