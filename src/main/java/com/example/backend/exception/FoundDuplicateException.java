package com.example.backend.exception;

public class FoundDuplicateException extends Exception{
    public FoundDuplicateException(){
        super("element is already present (duplicate problem).");
    }
}
