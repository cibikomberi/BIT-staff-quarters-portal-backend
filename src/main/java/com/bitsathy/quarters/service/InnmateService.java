package com.bitsathy.quarters.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bitsathy.quarters.model.Innmate;
import com.bitsathy.quarters.repo.InnmateRepo;


@Service
public class InnmateService {

    @Autowired
    private InnmateRepo innmateRepo;

    public List<Innmate> getInnmates(){
        return innmateRepo.findAll();
    }

    public List<Innmate> updateInnmates(List<Innmate> innmates){
        return innmateRepo.saveAll(innmates);
    }

    public Innmate addInnmates(Innmate innmate) {
        return innmateRepo.save(innmate);
    }
}
