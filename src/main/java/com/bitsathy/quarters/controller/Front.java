package com.bitsathy.quarters.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bitsathy.quarters.model.Users;
import com.bitsathy.quarters.service.UserService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@CrossOrigin(origins = "http://localhost:3000/")
public class Front {

    @Autowired
    private UserService userService;

    @GetMapping("/hello")
    public String helloString() {
        return "hello";
    }
    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody Users user) {
        // System.out.println(user);
        // return new ResponseEntity<String>("hi",HttpStatus.OK);
        return new ResponseEntity<>(userService.verify(user),HttpStatus.OK);
    }
    
    
    
}


record JwtResponse(String token) {}