package com.example.backend.repository;
import com.example.backend.model.Course;
import com.example.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Integer> {
    //List<Course> findAllByUser(User user);
    Course getCourseById(Integer id);
    Course findCourseById(Integer id);
}
