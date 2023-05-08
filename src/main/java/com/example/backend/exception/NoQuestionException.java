package com.example.backend.exception;

public class NoQuestionException  extends  Exception{
    public NoQuestionException(){
        super("The question you are looking for does not exist");
    }
}
