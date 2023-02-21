package com.example.backend.repository;
import com.example.backend.model.Role;
import com.example.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    long countByEmail(String email);
    long countByEmailAndPassword(String email, String password);
    User findUserByEmail(String email);
    User findUserByEmailAndPassword(String email, String password);
    User findUserById(Integer userId);
    List<User> findAllByRole(Role role);

    User getUserById(Integer userId);
}
