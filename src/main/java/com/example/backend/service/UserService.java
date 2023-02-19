package com.example.backend.service;

import com.example.backend.model.Role;
import com.example.backend.model.User;
import com.example.backend.repository.RoleRepository;
import com.example.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public List<User> getUsers(){
        return userRepository.findAll();
    }
    public User createStudent(User user) {
        if(userRepository.countByEmail(user.getEmail()) == 0){
            user.setRole(roleRepository.getRoleByName("student"));
            return userRepository.save(user);
        } else{
            return null;
        }
    }

    public User findUserByEmail(String email){
        return userRepository.findUserByEmail(email);
    }

    public User updateUserPasswordByEmail(User user){
        User userToUpdate = userRepository.findUserByEmail(user.getEmail());
        userToUpdate.setPassword(user.getPassword());
        userRepository.save(userToUpdate);
        return userToUpdate;
    }

    public User findUserById(Integer userId) {
        return userRepository.findUserById(userId);
    }
}
