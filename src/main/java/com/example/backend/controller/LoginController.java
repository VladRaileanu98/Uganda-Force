package com.example.backend.controller;

import com.example.backend.model.LoginRequest;
import com.example.backend.model.LoginResponse;
import com.example.backend.model.Role;
import com.example.backend.model.User;
import com.example.backend.repository.UserRepository;
import com.example.backend.service.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    JWTUtils jwtUtils;

    @Autowired
    UserRepository userRepository;

    @CrossOrigin(origins = {"*"})
    @RequestMapping(value = "/signIn", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity<LoginResponse> loginResponse(@RequestBody LoginRequest loginRequest){
        if(userRepository.countByEmailAndPassword(loginRequest.getEmail(),
                loginRequest.getPassword()) == 1){
            User user = userRepository.findUserByEmailAndPassword(loginRequest.getEmail(), loginRequest.getPassword());
            Role role = user.getRole();
            String token = jwtUtils.generateJWT(user.getId(), user.getEmail(), role.getName(), user.getFirstName(), user.getLastName());
            System.out.println(token);
            return new ResponseEntity<>(new LoginResponse(token, true),HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
}
