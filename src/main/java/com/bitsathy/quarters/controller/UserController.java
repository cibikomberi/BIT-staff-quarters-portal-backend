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

import com.bitsathy.quarters.model.Admin;
import com.bitsathy.quarters.model.Faculty;
import com.bitsathy.quarters.model.Handler;
import com.bitsathy.quarters.model.Users;
import com.bitsathy.quarters.service.UserService;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody ObjectNode json) {
        String username = json.get("username").asText();
        String password = json.get("password").asText();
        return new ResponseEntity<>(userService.verify(username, password), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody Users user) {
        user = userService.register(user);
        if (user == null) {
            return new ResponseEntity<>("Unable to create user", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/update/user/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN') or (hasAuthority('SCOPE_USER') and (#id == T(com.bitsathy.quarters.security.JwtUtils).getUserIdFromToken(authentication)))")
    public Users updateFaculty(@RequestBody Faculty user, @PathVariable Long id){   
        return userService.updateUser(user, id);
    }

    @PutMapping("/update/admin/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public Users updateAdmin(@RequestBody Admin user, @PathVariable Long id){
        return userService.updateUser(user, id);

    }

    @PutMapping("/update/handler/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN') or (hasAuthority('SCOPE_HANDLER') and (#id == T(com.bitsathy.quarters.security.JwtUtils).getUserIdFromToken(authentication)))")
    public Users updateHandler(@RequestBody Handler user, @PathVariable Long id){
        return userService.updateUser(user, id);

    }

    @GetMapping("/whoami")
    public Users whoAmI() {
        return userService.whoAmI();
    }

    @GetMapping("/whoisthis/{id}")
    public Users whoIsThis(@PathVariable Long id) {
        return userService.whoIsThis(id);
    }

    @GetMapping("/users")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public List<Users> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/users/search")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public List<Users> searchUsers(@RequestParam String keyword) {
        return userService.searchUsers(keyword);
    }
    
}

record JwtResponse(String token) {
}