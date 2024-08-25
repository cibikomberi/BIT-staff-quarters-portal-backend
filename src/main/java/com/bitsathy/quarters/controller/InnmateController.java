package com.bitsathy.quarters.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bitsathy.quarters.model.Innmate;
import com.bitsathy.quarters.service.InnmateService;



@RestController
@CrossOrigin
public class InnmateController {

    @Autowired
    private InnmateService innmateService;

    @GetMapping("/innmates")
    public List<Innmate> getInnmates(){
        return innmateService.getInnmates();
    }
}
