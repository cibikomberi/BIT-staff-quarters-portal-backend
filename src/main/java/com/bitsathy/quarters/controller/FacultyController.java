package com.bitsathy.quarters.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import com.bitsathy.quarters.model.Compliant;
import com.bitsathy.quarters.model.Faculty;
import com.bitsathy.quarters.service.FacultyService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@CrossOrigin
public class FacultyController {
    @Autowired
    private FacultyService facultyService;

    @GetMapping("/faculty")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public List<Faculty> getFaculty() {
        // System.out.println(username);
        return facultyService.getFaculty();
    }

    @GetMapping("/faculty/{id}")
    public ResponseEntity<?> getUserById(@PathVariable String id) {
        Faculty faculty = facultyService.getFacultyById(id);
        if (faculty == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(faculty, HttpStatus.OK);
    }
@GetMapping("/roles")
    public String getCurrentUserRoles() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication != null) {
            String roles = authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.joining(", "));
                    System.out.println(roles);
            return "Current user roles: " + roles;
        }
        
        return "No authenticated user";
    }
    @PostMapping("/test")
    public String postMethodName(@RequestBody Compliant ssid) {
        System.out.println(ssid);
        return "hi";
    }
    
    
    
}
