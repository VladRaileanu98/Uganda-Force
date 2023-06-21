package com.example.backend.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@CrossOrigin("*")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer noOfQuestions;

    public void incrementNoOfQuestions(){
        this.noOfQuestions = this.noOfQuestions + 1;
    }

    public void decrementNoOfQuestions(){
        this.noOfQuestions = this.noOfQuestions - 1;
    }

    private Boolean isVisible;

    private Integer timeLimit;


    @JsonFormat( pattern="yyyy-MM-dd" )
    private Date deadline;

    @OneToMany
    private List<Question> questionList = new ArrayList<>();

    @OneToMany
    List<Grade> gradeList = new ArrayList<>();

//    @ManyToOne
//    Course course;
    private Integer parentCourseId;

    private Integer lessonId;
}
