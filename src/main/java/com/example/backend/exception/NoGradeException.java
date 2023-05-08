package com.example.backend.exception;

public class NoGradeException extends Exception{
    public NoGradeException(){
        super("no grade found");
    }
}
