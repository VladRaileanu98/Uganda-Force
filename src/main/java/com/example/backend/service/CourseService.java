package com.example.backend.service;

import com.example.backend.model.*;
import com.example.backend.repository.*;
import lombok.RequiredArgsConstructor;
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
    private final UserRepository userRepository;
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
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
    public Course getCourseById(Integer courseId){
        return courseRepository.getCourseById(courseId);
    }
    public Course addCourse(Course course) {
        return courseRepository.save(course);
    }

}
