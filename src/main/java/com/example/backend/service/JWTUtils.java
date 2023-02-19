package com.example.backend.service;

import com.example.backend.model.LoginRequest;
import org.springframework.stereotype.Service;

import java.util.Date;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;

@Service
public class JWTUtils {
    private Algorithm algorithm = Algorithm.HMAC256("secret");

    public String generateJWT(Integer userId, String email, String role, String firstName, String lastName){
        return JWT.create()
                .withClaim("userId", userId)
                .withClaim("email", email)
                .withClaim("role", role)
                .withClaim("firstName", firstName)
                .withClaim("lastName", lastName)
                .withIssuer("issuer")
                .withIssuedAt(new Date())
                .sign(algorithm);
    }

    public boolean validate(String jwt) {
        JWTVerifier jwtVerifier = JWT.require(algorithm)
                .build();
        try {
            jwtVerifier.verify(jwt);
        } catch (Exception e) {
            return false;
        }
        return true;
    }


}
