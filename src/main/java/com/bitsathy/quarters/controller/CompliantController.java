package com.bitsathy.quarters.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.bitsathy.quarters.model.Compliant;
import com.bitsathy.quarters.service.CompliantService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@CrossOrigin
public class CompliantController {

    @Autowired
    private CompliantService compliantService;

    @GetMapping("/compliants")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<List<Compliant>> getAllCompliants() {
        return new ResponseEntity<>(compliantService.getAllCompliants(), HttpStatus.OK);
    }

    @GetMapping("/compliants/{username}")
    @PreAuthorize("hasAuthority('SCOPE_USER') and #username == authentication.name")
    public ResponseEntity<List<Compliant>> getAllCompliantsByUser(@PathVariable String username) {
        return new ResponseEntity<>(compliantService.getAllCompliantsByUser(username), HttpStatus.OK);
    }

    @GetMapping("/compliant/{id}")
    @PostAuthorize("returnObject.body == null || (hasAuthority('SCOPE_ADMIN') or (hasAuthority('SCOPE_USER') and returnObject.body.issuedBy == authentication.name))")
    public ResponseEntity<?> getCompliantById(@PathVariable Integer id) {
        Compliant compliant = compliantService.getCompliantById(id);
        if (compliant == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(compliant, HttpStatus.OK);
    }

    @PostMapping("/compliants")
    @PreAuthorize("(hasAuthority('SCOPE_USER') or hasAuthority('SCOPE_ADMIN')) and #compliant.issuedBy == authentication.name")
    public ResponseEntity<?> newCompliant(@RequestBody Compliant compliant) {
        try {
            return new ResponseEntity<>(compliantService.addCompliant(compliant), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
