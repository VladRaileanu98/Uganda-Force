package com.example.backend.exception;

public class NoCourseException extends  Exception{
    public NoCourseException(){
        super("no course found");
    }
}
