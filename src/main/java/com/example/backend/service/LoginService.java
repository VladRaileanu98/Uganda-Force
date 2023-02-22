package com.example.backend.service;

import com.example.backend.model.LoginRequest;
import com.example.backend.model.LoginResponse;
import com.example.backend.model.Role;
import com.example.backend.model.User;
import com.example.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final JWTUtils jwtUtils;
    private final UserRepository userRepository;
    public ResponseEntity<LoginResponse> loginResponse(LoginRequest loginRequest){
        if(userRepository.countByEmailAndPassword(loginRequest.getEmail(),
                loginRequest.getPassword()) == 1){
            User user = userRepository.findUserByEmailAndPassword(loginRequest.getEmail(), loginRequest.getPassword());
            Role role = user.getRole();
            String token = jwtUtils.generateJWT(user.getId(), user.getEmail(), role.getName(), user.getFirstName(), user.getLastName());
            System.out.println(token);
            return new ResponseEntity<>(new LoginResponse(token, true), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
