package com.example.backend.model;

public class LoginResponse {
    private String jwt;
    private boolean isSuccessful;
    public LoginResponse(String jwt, boolean isSuccessful){
        this.jwt = jwt;
        this.isSuccessful = isSuccessful;
    }

    public String getJwt() {
        return jwt;
    }

    public boolean isSuccessful() {
        return isSuccessful;
    }
}
