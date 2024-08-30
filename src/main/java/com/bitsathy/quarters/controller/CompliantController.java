package com.bitsathy.quarters.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<Compliant>> getCompliants(){
        return new ResponseEntity<>( compliantService.getAllCompliants(), HttpStatus.OK);
    }
    @GetMapping("/compliant/{id}")
    public ResponseEntity<?> getCompliantById(@PathVariable Integer id) {
        Compliant compliant = compliantService.getCompliantById(id);
        if(compliant == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(compliant, HttpStatus.OK);
    }
    

    @PostMapping("/compliants")
    public Compliant postMethodName(@RequestBody Compliant compliant) {        
        return compliantService.addCompliant(compliant);
    }
    
}
