package com.bitsathy.quarters.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import com.bitsathy.quarters.model.Faculty;
import com.bitsathy.quarters.service.FacultyService;

import org.springframework.web.bind.annotation.GetMapping;

@RestController
@CrossOrigin
public class FacultyController {
    @Autowired
    private FacultyService facultyService;

    @GetMapping("/faculty")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public List<Faculty> getFaculty() {
        return facultyService.getFaculty();
    }

    @GetMapping("/roles")
    public String getCurrentUserRoles() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            System.out.println(authentication.getName());
            String roles = authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.joining(", "));
            System.out.println(roles);
            return "Current user roles: " + roles;
        }
        return "No authenticated user";
    }
}