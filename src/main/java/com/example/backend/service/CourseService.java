package com.example.backend.service;

//import com.example.backend.exception.NoCourseException;
import com.example.backend.model.*;
import com.example.backend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;



import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.storage.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
//    private final UploadedFileRepository uploadedFileRepository;
//
//    private final QuizRepository quizRepository;
//
//    private final UserCourseRepository userCourseRepository;

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

//    public List<Course> getCoursesByUserId(Integer userId) {
//
//
//        Set<UserCourse> userCourses = userCourseRepository.findAllByUserId(userId);
//
//        List<Integer> courseIdsLinkedToUser = new ArrayList<>();
//
//        for(UserCourse userCourse : userCourses){
//            courseIdsLinkedToUser.add(userCourse.getCourseId());
//        }
//
//        List<Course> courseLinkedToUser = new ArrayList<>();
//
//        for(int i = 0; i < courseIdsLinkedToUser.size(); i++) {
//
//            courseLinkedToUser.add(courseRepository.getCourseById(courseIdsLinkedToUser.get(i)));
//        }
//
//
//        return courseLinkedToUser;
//    }

//    public List<Course> getCoursesByCategoryName(String categoryName) {
//
//        return courseRepository.findAllByCategoryList_Name(categoryName);
//    }


//    public void uploadObject(List<MultipartFile> multipartFile, Integer courseId, Boolean shared) throws IOException {
//        String projectId = "amiable-catfish-363617";
//        String bucketName = "e-learning-storage";
//        String filePath = "1";
//
//        File credentialsPath = new File("D:\\DB\\repos\\E-Learning\\backend\\src\\main\\java\\com\\example\\backend\\controller\\amiable-catfish-363617-5663d009dcc5.json");
//        GoogleCredentials credentials;
//        try (FileInputStream serviceAccountStream = new FileInputStream(credentialsPath)) {
//            credentials = ServiceAccountCredentials.fromStream(serviceAccountStream);
//        }
//
//        Storage storage = StorageOptions.newBuilder().setProjectId(projectId).setCredentials(credentials).build().getService();
//        for (MultipartFile file : multipartFile) {
//            String objectName = file.getOriginalFilename();
//            int i=0;
//            while ((objectName.charAt(i) >= 48 && objectName.charAt(i) <= 57))
//                i++;
//            objectName=objectName.substring(i);
//
//            if(shared){
//                objectName=courseId+"/"+"shared/"+objectName;
//            }
//            else{
//                objectName=courseId+"/"+objectName;
//            }
//            Course course=courseRepository.getCourseById(courseId);
//            UploadedFile uploadedFileName=UploadedFile.builder().fileName(objectName).build();
//            uploadedFileName.setCourse(course);
//            uploadedFileName=uploadedFileRepository.save(uploadedFileName);
//            course=courseRepository.save(course);
//
//            BlobId blobId = BlobId.of(bucketName, objectName);
//            BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(file.getContentType())
//                    .build();
//            storage.createFrom(blobInfo, file.getInputStream());
//
//            System.out.println(
//                    "File " + filePath + " uploaded to bucket " + bucketName + " as " + objectName);
//        }
//    }

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
//
//    public List<UploadedFile> getAllFiles(Integer courseId){
//        return uploadedFileRepository.getAllByCourse_IdAndFileNameNotContaining(courseId, "shared");
//    }
//
//    public List<UploadedFile> getAllFiles2(Integer courseId){
//        return uploadedFileRepository.getAllByCourse_IdAndFileNameContaining(courseId, "shared");
//    }

//    public List<Quiz> getAllQuizes(Integer courseId){
//        return quizRepository.getAllByCourse_Id(courseId);
//    }

    public Course addCourse(Course course) {
        return courseRepository.save(course);
    }

    //adauga de 'vlad'
//    public List<Quiz> getAllQuizzesByCourse(Integer courseId) throws NoCourseException {
//        Optional<Course> optionalCourse = courseRepository.findById(courseId);
//
//        if(!optionalCourse.isPresent()) throw new NoCourseException();
//        else{
//            return optionalCourse.get().getQuizList();
//        }
//    }


}
