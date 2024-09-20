package com.bitsathy.quarters.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
import org.springframework.web.bind.annotation.RequestPart;

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

    @PostMapping("/register/user")
    public ResponseEntity<?> registerUser(@RequestPart Faculty data, @RequestPart(required = false) MultipartFile image,
            @RequestPart String password) throws IOException {
        try {
            userService.verifyFaculty(data);
            data.setRoles("USER");
            return new ResponseEntity<>(userService.register(data, image, password), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping("/register/handler")
    public ResponseEntity<?> registerHandler(@RequestPart Handler data,
            @RequestPart(required = false) MultipartFile image, @RequestPart(required = false) String password) throws IOException {
        try {
            userService.verifyHandler(data);
            data.setRoles("HANDLER");
            return new ResponseEntity<>(userService.register(data, image, password), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update/user/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN') or (hasAuthority('SCOPE_USER') and (#id == T(com.bitsathy.quarters.security.JwtUtils).getUserIdFromToken(authentication)))")
    public ResponseEntity<?> updateFaculty(@RequestPart Faculty data,
            @RequestPart(required = false) MultipartFile image, @PathVariable Long id) throws IOException {
        try {
            userService.verifyFaculty(data);
            data.setRoles("USER");
            return new ResponseEntity<>(userService.updateUser(data, image, id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update/admin/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<?> updateAdmin(@RequestPart Admin data, @RequestPart(required = false) MultipartFile image,
            @PathVariable Long id) throws IOException {
        try {
            userService.verifyAdmin(data);
            data.setRoles("ADMIN");
            return new ResponseEntity<>(userService.updateUser(data, image, id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update/handler/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN') or (hasAuthority('SCOPE_HANDLER') and (#id == T(com.bitsathy.quarters.security.JwtUtils).getUserIdFromToken(authentication)))")
    public ResponseEntity<?> updateHandler(@RequestPart Handler data,
            @RequestPart(required = false) MultipartFile image, @PathVariable Long id) throws IOException {
        try {
            userService.verifyHandler(data);
            data.setRoles("HANDLER");
            return new ResponseEntity<>(userService.updateUser(data, image, id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/whoami")
    public Users whoAmI() {
        return userService.whoAmI();
    }

    @GetMapping("/whoisthis/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public Users whoIsThis(@PathVariable Long id) {
        return userService.whoIsThis(id);
    }

    @GetMapping("/users")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public List<Users> getUsers() {
        return userService.getUsers();
    }

    @PostMapping("users/changePassword/{id}")
    @PreAuthorize("#id == T(com.bitsathy.quarters.security.JwtUtils).getUserIdFromToken(authentication)")
    public ResponseEntity<?> changePassword(@PathVariable Long id, @RequestBody ObjectNode json) {
        try {
            return new ResponseEntity<>(userService.changePassword(id, json.get("password").asText()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/users/search")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public List<Users> searchUsers(@RequestParam String keyword) {
        return userService.searchUsers(keyword);
    }

    @GetMapping("/users/image/{id}")
    @Transactional(readOnly = true)
    public ResponseEntity<byte[]> getProfileImage(@PathVariable Long id) {
        return userService.getProfilePic(id);
    }
}

record JwtResponse(String token) {
}