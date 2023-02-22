package com.example.backend.controller;

import com.example.backend.model.LoginRequest;
import com.example.backend.model.LoginResponse;
import com.example.backend.model.Role;
import com.example.backend.model.User;
import com.example.backend.repository.UserRepository;
import com.example.backend.service.JWTUtils;
import com.example.backend.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/login")
public class LoginController {
    private final LoginService loginService;

    @CrossOrigin(origins = {"*"})
    @RequestMapping(value = "/signIn", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity<LoginResponse> loginResponse(@RequestBody LoginRequest loginRequest){
        return loginService.loginResponse(loginRequest);
    }
}
