package com.example.backend.exception;

public class NoQuizException extends Exception{
    public NoQuizException(){
        super("THe quiz you are looking for doesnt exist");
    }
}
