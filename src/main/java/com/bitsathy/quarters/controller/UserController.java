package com.bitsathy.quarters.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/whoami")
    public Users whoAmI() { 
        return userService.whoAmI();
    }

    @GetMapping("/whoisthis/{id}")
    public Users whoIsThis(@PathVariable String id) {
        return userService.whoIsThis(id);
    }

    @GetMapping("/users")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public List<Users> getUsers() {
        return userService.getUsers();
    }    
}

record JwtResponse(String token) {}