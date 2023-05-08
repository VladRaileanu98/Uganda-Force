package com.example.backend.repository;

import com.example.backend.model.Grade;
import com.example.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GradeRepository extends JpaRepository<Grade, Integer> {
    List<Grade> getAllByUser_Id(Integer id);
    List<Grade> getAllByUser(User user);
}
