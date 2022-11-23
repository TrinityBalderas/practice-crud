package com.example.practice.Service;

import com.example.practice.exception.NameLengthException;
import com.example.practice.exception.NameNotFoundException;
import com.example.practice.model.PracticeModel;
import com.example.practice.repository.PracticeRepository;
import com.example.practice.service.PracticeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class PraacticeServiceTest {
    private PracticeRepository mockPracticeReopsitory;
    private PracticeModel insideDatabase;
    private PracticeService practiceService;

    @BeforeEach
    public void setup() {
        insideDatabase = new PracticeModel();
        insideDatabase.setId(1L);
        insideDatabase.setName("Dumb");
        mockPracticeReopsitory = mock(PracticeRepository.class);
        practiceService = new PracticeService(mockPracticeReopsitory);
        when(mockPracticeReopsitory.findById(1L)).thenReturn(Optional.of(insideDatabase));
        when(mockPracticeReopsitory.save(any())).thenAnswer(method -> method.getArgument(0));
    }

    @Test
    public void getAllNames(){
        practiceService.getAllNames();
        verify(mockPracticeReopsitory).findAll();
    }

    @Test
    public void getById_IfSearchResultIsEmptyThenReturnError(){
        NameNotFoundException exception = assertThrows(NameNotFoundException.class, () -> {
            practiceService.getNameById(2L);
        });
        assertEquals("Name 2 Not Found", exception.getMessage());
    }

    @Test
    public void getNameById_getByRepo(){
        Mockito.when(mockPracticeReopsitory.findById(1L)).thenReturn(Optional.of(insideDatabase));

        PracticeModel actual = practiceService.getNameById(1L);

        assertEquals(1L, actual.getId());
    }

    @Test
    public void create_shouldThrowErrorifNameIsntOver1Length(){
        PracticeModel practiceModel = new PracticeModel();
        practiceModel.setName("A");
        NameLengthException exception = assertThrows(NameLengthException.class, () -> {
            practiceService.createName(practiceModel);
        });
        assertEquals("A is too short", exception.getMessage());
    }

    @Test
    public void create_shouldTakeTheFirstLetterMakeItUpperCase(){
        PracticeModel input = new PracticeModel();
        input.setName("cali");
        PracticeModel actual = practiceService.createName(input);
        assertEquals("Cali", actual.getName());
    }

    @Test
    public void update_shouldOnlyUpdateName(){
       PracticeModel input = new PracticeModel();
       input.setName("Zoe");
        PracticeModel actual = practiceService.updateName(1L, input);

        Mockito.verify(mockPracticeReopsitory).save(any());
        assertEquals("Zoe", actual.getName());
    }

    @Test
    public void update_UpdateIfNotNull(){
        PracticeModel input =  new PracticeModel();
        PracticeModel actual = practiceService.updateName(1L, input);
        Mockito.verify(mockPracticeReopsitory).save(any());
        assertEquals(insideDatabase, actual);
    }

    @Test
    public void delete_isDeletedByRepo(){
        Mockito.when(mockPracticeReopsitory.existsById(1L)).thenReturn(true);
        practiceService.deleteName(1L);
        Mockito.verify(mockPracticeReopsitory).deleteById(1L);
    }

    @Test
    public void delete_ifInputDoesNotExistThenDoesNothing(){
        Mockito.when(mockPracticeReopsitory.existsById(2L)).thenReturn(false);

        practiceService.deleteName(2L);

        Mockito.verify(mockPracticeReopsitory, times(0)).deleteById(1L);
    }
}