package com.example.practice.controller;

import com.example.practice.exception.NameLengthException;
import com.example.practice.exception.NameNotFoundException;
import com.example.practice.model.PracticeModel;
import com.example.practice.responses.PracticeResponse;
import com.example.practice.service.PracticeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class PracticeControllerTest {
    PracticeService mockPracticeService;
    PracticeController practiceController;

    @BeforeEach
    public void set(){
        mockPracticeService = mock(PracticeService.class);
        practiceController = new PracticeController(mockPracticeService);
    }
    @Test
    public void getAll_CallsService(){
        practiceController.getAllNames();
        verify(mockPracticeService).getAllNames();
    }
    @Test
    public void getAll_ReturnValuesFromService(){
        when(mockPracticeService.getAllNames()).thenReturn(null);
        assertEquals(null, practiceController.getAllNames());
    }
    @Test
    public void getNameById_shouldReturn404WhenRecordDoesnNotExist(){
        Mockito.when(mockPracticeService.getNameById(1L)).thenThrow(new NameNotFoundException("Name 1 Not Found"));

        ResponseEntity<PracticeModel> actual = practiceController.getNameById(1L);

        assertEquals(HttpStatus.NOT_FOUND, actual.getStatusCode());
    }

    @Test
    public void createName_shouldReturn400WhenRecordDoesNotReachOver2Length(){
        PracticeModel input = new PracticeModel();
        input.setName("B");
        Mockito.when(mockPracticeService.createName(input)).thenThrow(new NameLengthException("B is too short"));

        ResponseEntity<PracticeModel> actual = practiceController.createName(input);

        assertEquals(HttpStatus.BAD_REQUEST, actual.getStatusCode());

    }

    @Test
    public void update_shouldReturn404WhenRecordDoesNotExist(){
        PracticeModel input = new PracticeModel();
        Mockito.when(mockPracticeService.updateName(2L, input)).thenThrow(new NameNotFoundException("Name 2 Not Found"));

        ResponseEntity<PracticeModel> actual = practiceController.updateName(2L, input);

        assertEquals(HttpStatus.NOT_FOUND, actual.getStatusCode());
    }
    @Test
    public void delete_isDeletedBService(){
        practiceController.deleteName(2L);
        Mockito.verify(mockPracticeService).deleteName(2L);
    }
    @Test
    public void delete_taskCompletedMessage(){
        PracticeModel practiceModel = new PracticeModel();

        ResponseEntity<PracticeResponse> actual = practiceController.deleteName(1L);

        assertEquals(HttpStatus.OK, actual.getStatusCode());
        assertEquals("Name 1 was deleted.", actual.getBody().message);
    }
}
