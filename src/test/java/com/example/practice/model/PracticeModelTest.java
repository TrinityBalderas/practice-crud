package com.example.practice.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
public class PracticeModelTest {

    @Test
    public void id_SetterAndGetter(){
        PracticeModel input = new PracticeModel();
        input.setId(1L);
        assertEquals(1l, input.getId());
    }
}
