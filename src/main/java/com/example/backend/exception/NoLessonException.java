package com.example.backend.exception;

public class NoLessonException extends Exception{
    public NoLessonException(){
        super("THe lesson you are looking for doesnt exist");
    }
}
