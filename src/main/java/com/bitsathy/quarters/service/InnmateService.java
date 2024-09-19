package com.bitsathy.quarters.service;

import java.util.List;

import com.bitsathy.quarters.model.Faculty;
import com.bitsathy.quarters.repo.FacultyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bitsathy.quarters.model.Innmate;
import com.bitsathy.quarters.repo.InnmateRepo;


@Service
public class InnmateService {

    @Autowired
    private InnmateRepo innmateRepo;

    @Autowired
    private FacultyRepo facultyRepo;



    public List<Innmate> getInnmates(){
        return innmateRepo.findAll();
    }

    public List<Innmate> updateInnmates(List<Innmate> innmates){
        return innmateRepo.saveAll(innmates);
    }

    public Innmate addInnmates(Innmate innmate,Long id) throws Exception{
        Faculty faculty = Faculty.builder().id(id).build();
        innmate.setFaculty(faculty);

        return innmateRepo.save(innmate);
    }

    public List<Innmate> getInnmatesByUser(Long id) {
        return facultyRepo.findById(id).get().getInnmates();
    }

    public List<Innmate> searchInnmate(String keyword) {
        return innmateRepo.searchInnmates(keyword);
    }

    public void innmatesCheckout(List<Long> innmates, Long facultyId) {
        innmates.forEach(id -> {
            Innmate innmate = innmateRepo.findById(id).get();
            if (innmate.getFaculty().getId() == facultyId) {
                innmateRepo.delete(innmate);
            }
        });
    }
}
