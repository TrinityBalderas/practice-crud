package com.example.practice.responses;

import com.example.practice.model.PracticeModel;

public class PracticeResponse {
    public String message;
    public PracticeModel body;

    public PracticeResponse (PracticeModel practiceModel, String message){
        this.message = message;
        this.body = practiceModel;
    }
}
