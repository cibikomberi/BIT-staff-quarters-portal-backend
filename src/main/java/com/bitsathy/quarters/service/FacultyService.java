package com.bitsathy.quarters.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bitsathy.quarters.model.Faculty;
import com.bitsathy.quarters.repo.FacultyRepo;

@Service
public class FacultyService {
    @Autowired
    private FacultyRepo facultyRepo;

    public List<Faculty> getFaculty(){
        return facultyRepo.findAll();
    }

    public Faculty getFacultyById(Long id){
        return facultyRepo.findById(id).orElse(null);
    }
}
