package com.example.backend.service;

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
import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public ResponseEntity<Course> getCourseById(Integer courseId){
        Course course= courseRepository.findById(courseId)
                .orElseThrow(()-> new RemoteStorageHelper.StorageHelperException("Course doesnt exist"));
        return ResponseEntity.ok(course);
    }

    public Course addCourse(Course course) {
        return courseRepository.save(course);
    }

    public Course updateCourse(Integer id, Course course){
        Course updatedCourse = courseRepository.findCourseById(id);
        updatedCourse.setName(course.getName());
        updatedCourse.setDescription(course.getDescription());
        courseRepository.save(updatedCourse);
        return updatedCourse;
    }

    public boolean deleteCourse(Integer id){
        Course course = courseRepository.findCourseById(id);
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
