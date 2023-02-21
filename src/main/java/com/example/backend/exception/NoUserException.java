package com.example.backend.exception;

public class NoUserException extends Exception{
    public NoUserException(){
        super("user does not exist");
    }
}
