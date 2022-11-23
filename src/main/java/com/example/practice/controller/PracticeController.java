package com.example.practice.controller;

import com.example.practice.exception.NameLengthException;
import com.example.practice.exception.NameNotFoundException;
import com.example.practice.model.PracticeModel;
import com.example.practice.responses.PracticeResponse;
import com.example.practice.service.PracticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("practice")
public class PracticeController {
    private PracticeService practiceService;
    @Autowired
    public PracticeController(PracticeService practiceService){
        this.practiceService = practiceService;
    }
    @GetMapping
    public List<PracticeModel> getAllNames(){
        return practiceService.getAllNames();
    }
    @GetMapping("{id}")
    public ResponseEntity<PracticeModel> getNameById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(practiceService.getNameById(id));
        } catch (NameNotFoundException e){ // <---- should not be this
            return new ResponseEntity(new PracticeResponse(null, e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping
    public ResponseEntity<PracticeModel> createName(@RequestBody PracticeModel body){
        try {
            return ResponseEntity.ok(practiceService.createName(body));
        }catch (NameLengthException e){
            return new ResponseEntity(new PracticeResponse(null, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping("{id}")
    public ResponseEntity<PracticeModel> updateName(@PathVariable Long id, @RequestBody PracticeModel body){
        try {
            return ResponseEntity.ok(practiceService.updateName(id, body));
        } catch (NameNotFoundException e){ // <---- should not be this
            return new ResponseEntity(new PracticeResponse(null, e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("{id}")
    public ResponseEntity<PracticeResponse> deleteName(@PathVariable Long id){
        practiceService.deleteName(id);
        return ResponseEntity.ok(new PracticeResponse(null, "Name " + id + " was deleted."));
    }
}
