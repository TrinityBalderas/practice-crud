package com.example.practice.service;

import com.example.practice.exception.NameLengthException;
import com.example.practice.exception.NameNotFoundException;
import com.example.practice.model.PracticeModel;
import com.example.practice.repository.PracticeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class PracticeService {
    private final PracticeRepository practiceRepository;

    public PracticeService(PracticeRepository practiceRepository){
        this.practiceRepository = practiceRepository;
    }
    public List<PracticeModel> getAllNames(){
        return practiceRepository.findAll();
    }
    public PracticeModel getNameById(Long id){
        Optional<PracticeModel> searchResult = practiceRepository.findById(id);
        if(searchResult.isEmpty()){
            throw new NameNotFoundException("Name " + id + " Not Found");
        }
        return searchResult.get();
    }
    public PracticeModel createName(PracticeModel body){
        if(body.getName().length() < 2){
            throw new NameLengthException(body.getName() + " is too short");
        }
        PracticeModel person = new PracticeModel();
        String name = body.getName();
        String letter = (name.substring(0,1));
        letter = letter.toUpperCase();
        String restOfWord = (name.substring(1));
        String newName = letter+restOfWord;
        person.setName(newName);
        PracticeModel saved = practiceRepository.save(person);
        return saved;
    }
    public PracticeModel updateName(Long id, PracticeModel body) {
        body.setId(id);
        PracticeModel original = practiceRepository.findById(id).get();
        if(body.getName() != null){
            original.setName(body.getName());
        }
        return practiceRepository.save(original);
    }
    public void deleteName(Long id) {
        if (practiceRepository.existsById(id)){
            practiceRepository.deleteById(id);
        }
    }
}
