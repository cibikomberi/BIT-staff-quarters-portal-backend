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
    public List<Innmate> getInnmates() {
        return innmateService.getInnmates();
    }

    @GetMapping("/innmates/search")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public List<Innmate> searchInnmate(@RequestParam String keyword) {
        return innmateService.searchInnmate(keyword);
    }

    @GetMapping("/innmates/{id}")
    @PreAuthorize("hasAuthority('SCOPE_USER') and (#id == T(com.bitsathy.quarters.security.JwtUtils).getUserIdFromToken(authentication))")
    public List<Innmate> getInnmatesByUser(@PathVariable Long id) {
        return innmateService.getInnmatesByUser(id);
    }

    @PostMapping("/innmates/{id}")
    @PreAuthorize("hasAuthority('SCOPE_USER') and (#id == T(com.bitsathy.quarters.security.JwtUtils).getUserIdFromToken(authentication))")
    public ResponseEntity<?> addInnmates(@RequestBody Innmate innmate, @PathVariable Long id) {
        System.out.println(innmate);
        System.out.println(id);
        try {
            return new ResponseEntity<>(innmateService.addInnmates(innmate, id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/innmates/{id}")
    @PreAuthorize("hasAuthority('SCOPE_USER') and (#id == T(com.bitsathy.quarters.security.JwtUtils).getUserIdFromToken(authentication))")
    public List<Innmate> updateInnmates(@RequestBody List<Innmate> innmates, @PathVariable Long id) {
        System.out.println(innmates);
        return innmateService.updateInnmates(innmates);
    }

    @PostMapping("/innmates/checkout/{id}")
    @PreAuthorize("hasAuthority('SCOPE_USER') and (#id == T(com.bitsathy.quarters.security.JwtUtils).getUserIdFromToken(authentication))")
    public void innmatesCheckout(@RequestBody List<Long> innmates, @PathVariable Long id) {
        innmateService.innmatesCheckout(innmates, id);
    }
}