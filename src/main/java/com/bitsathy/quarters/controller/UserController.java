package com.bitsathy.quarters.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bitsathy.quarters.model.Users;
import com.bitsathy.quarters.service.UserService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/hello")
    public String helloString() {
        return "hello";
    }
    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody Users user) {
        return new ResponseEntity<>(userService.verify(user),HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<? extends Object> registerUser(@RequestBody Users user) {
        user = userService.register(user);
        if (user == null) {
            return new ResponseEntity<>("Unable to create user",HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/whoami")
    public Users whoAmI() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Jwt a = (Jwt)authentication.getPrincipal();
        return userService.whoAmI(a.getSubject());
    }

    @PutMapping("/update")
    public ResponseEntity<? extends Object> updateUser(@RequestBody Users user) {
        System.out.println(user);
        try {
            userService.updateUser(user);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

record JwtResponse(String token) {}