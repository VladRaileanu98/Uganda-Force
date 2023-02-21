package com.example.backend.exception;

public class NoRoleException extends Exception {
    public NoRoleException(){
        super("the role does not exist");
    }
}
