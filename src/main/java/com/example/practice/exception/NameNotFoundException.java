package com.example.practice.exception;

public class NameNotFoundException extends RuntimeException{
    public NameNotFoundException(String message){
        super(message);
    }
}
