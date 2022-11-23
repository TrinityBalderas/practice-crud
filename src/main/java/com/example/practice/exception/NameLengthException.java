package com.example.practice.exception;

public class NameLengthException extends RuntimeException{
    public NameLengthException(String message){
        super(message);
    }
}
