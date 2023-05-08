package com.example.backend.exception;

public class NoChoiceException extends Exception{
    public NoChoiceException(){
        super("no choice found");
    }
}
