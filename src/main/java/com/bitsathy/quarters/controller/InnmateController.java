package com.bitsathy.quarters.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bitsathy.quarters.model.Innmate;
import com.bitsathy.quarters.service.InnmateService;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@CrossOrigin
public class InnmateController {

    @Autowired
    private InnmateService innmateService;

    @GetMapping("/innmates")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public List<Innmate> getInnmates(){
        return innmateService.getInnmates();
    }
    
    @GetMapping("/innmates/search")
    public List<Innmate> searchInnmate(@RequestParam String keyword) {
        return innmateService.searchInnmate(keyword);
    }

    @GetMapping("/innmates/{username}")
    @PreAuthorize("hasAuthority('SCOPE_USER') and #username == authentication.name")
    public List<Innmate> getInnmatesByUser(@PathVariable String username){
        return innmateService.getInnmatesByUser(username);
    }

    @PostMapping("/innmates")
    @PreAuthorize("hasAuthority('SCOPE_USER') and #innmate.username == authentication.name")
    public ResponseEntity<?> addInnmates(@RequestBody Innmate innmate) {
        try {
            return new ResponseEntity<>(innmateService.addInnmates(innmate),HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/innmates")
    @PreAuthorize("hasAuthority('SCOPE_USER') and #innmate.username == authentication.name")
    public List<Innmate> updateInnmates(@RequestBody List<Innmate>  innmates) {
        System.out.println(innmates);
        return innmateService.updateInnmates(innmates);
    }
}