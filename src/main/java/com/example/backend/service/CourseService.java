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
            quizOptional.get().setCourse(courseOptional.get());
            quizRepository.save(quizOptional.get());
            courseRepository.save(courseOptional.get());
        }
    }

    public boolean deleteCourse(Integer id){
        Course course = courseRepository.getCourseById(id);
        courseRepository.delete(course);
        return true;
    }

    public void downloadObject(Integer courseId, String objectName, Boolean shared) {
        String projectId = "amiable-catfish-363617";
        String bucketName = "e-learning-storage";
        String home = System.getProperty("user.home");
        String filePath = home + "/Downloads/" + objectName;
        System.out.println(objectName);
        if(shared){
            objectName=courseId+"/shared/"+objectName;
        } else {
            objectName=courseId+"/"+objectName;
        }


        File credentialsPath = new File("D:\\DB\\repos\\E-Learning\\backend\\src\\main\\java\\com\\example\\backend\\controller\\amiable-catfish-363617-5663d009dcc5.json");
        GoogleCredentials credentials;
        try (FileInputStream serviceAccountStream = new FileInputStream(credentialsPath)) {
            credentials = ServiceAccountCredentials.fromStream(serviceAccountStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Storage storage = StorageOptions.newBuilder().setProjectId(projectId).setCredentials(credentials).build().getService();

        Blob blob = storage.get(BlobId.of(bucketName, objectName));
        blob.downloadTo(Paths.get(filePath));

        System.out.println(
                "Downloaded object "
                        + objectName
                        + " from bucket name "
                        + bucketName
                        + " to "
                        + filePath);
    }
}
