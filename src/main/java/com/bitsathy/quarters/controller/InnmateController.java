package com.bitsathy.quarters.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bitsathy.quarters.model.Innmate;
import com.bitsathy.quarters.service.InnmateService;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;




@RestController
@CrossOrigin
public class InnmateController {

    @Autowired
    private InnmateService innmateService;

    @GetMapping("/innmates")
    public List<Innmate> getInnmates(){
        return innmateService.getInnmates();
    }

    @PostMapping("/innmates")
    public Innmate addInnmates(@RequestBody Innmate  innmate) {
        return innmateService.addInnmates(innmate);
    }

    @PutMapping("/innmates")
    public List<Innmate> updateInnmates(@RequestBody List<Innmate>  innmates) {
        System.out.println(innmates);
        return innmateService.updateInnmates(innmates);
    }
    
}
