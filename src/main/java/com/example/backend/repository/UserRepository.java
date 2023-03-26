package com.example.backend.repository;
import com.example.backend.model.Role;
import com.example.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    long countByEmail(String email);
    long countByEmailAndPassword(String email, String password);
    User findUserByEmail(String email);
    User findUserByEmailAndPassword(String email, String password);
    User findUserById(Integer userId);


    User getUserById(Integer userId);

    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

}
