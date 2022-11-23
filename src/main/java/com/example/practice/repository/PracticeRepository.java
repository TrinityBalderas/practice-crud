package com.example.practice.repository;

import com.example.practice.model.PracticeModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PracticeRepository extends JpaRepository<PracticeModel, Long> {

}
