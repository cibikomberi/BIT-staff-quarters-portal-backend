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

    public Innmate addInnmates(Innmate innmate) throws Exception{
        System.out.println(innmate.getUsername());
        if (innmate.getUsername() == null) {
            throw new Exception("User id cannot be null");
        }
        return innmateRepo.save(innmate);
    }

    public List<Innmate> getInnmatesByUser(String username) {
        return innmateRepo.findByUsername(username);
    }

    public List<Innmate> searchInnmate(String keyword) {
        return innmateRepo.searchInnmates(keyword);
    }
}
