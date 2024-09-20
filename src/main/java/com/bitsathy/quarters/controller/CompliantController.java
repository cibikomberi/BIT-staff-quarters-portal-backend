package com.bitsathy.quarters.controller;

import java.util.List;
import java.util.Map;

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
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

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

    @GetMapping("/compliants/count")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<Map<String, Long>> getCompliantsCount() {
        return new ResponseEntity<>(compliantService.getCompliantCount(), HttpStatus.OK);
    }

    @GetMapping("/compliants/{id}") // TODO
    @PostAuthorize("returnObject.body == null or (hasAuthority('SCOPE_ADMIN') or (hasAuthority('SCOPE_USER') and returnObject.body.issuedBy.id == T(com.bitsathy.quarters.security.JwtUtils).getUserIdFromToken(authentication)) or (hasAuthority('SCOPE_HANDLER') and returnObject.body.assignedTo.id == T(com.bitsathy.quarters.security.JwtUtils).getUserIdFromToken(authentication)))")
    public ResponseEntity<?> getCompliantById(@PathVariable Long id) {
        Compliant compliant = compliantService.getCompliantById(id);
        if (compliant == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(compliant, HttpStatus.OK);
    }

    @GetMapping("/compliants/user/{id}") // TODO
    @PreAuthorize("hasAuthority('SCOPE_USER') and (#id == T(com.bitsathy.quarters.security.JwtUtils).getUserIdFromToken(authentication))")
    public ResponseEntity<List<Compliant>> getAllCompliantsByUser(@PathVariable Long id) {
        return new ResponseEntity<>(compliantService.getAllCompliantsByUser(id), HttpStatus.OK);
    }

    @PostMapping("/compliants/{id}")
    @PreAuthorize("hasAuthority('SCOPE_USER') and (#id == T(com.bitsathy.quarters.security.JwtUtils).getUserIdFromToken(authentication))")
    public ResponseEntity<?> newCompliant(@RequestBody Compliant compliant, @PathVariable Long id) {
        try {
            return new ResponseEntity<>(compliantService.newCompliant(compliant, id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/compliants/handler/{id}")
    @PreAuthorize("hasAuthority('SCOPE_HANDLER') and (#id == T(com.bitsathy.quarters.security.JwtUtils).getUserIdFromToken(authentication))")
    public ResponseEntity<List<Compliant>> getAllCompliantsByHandler(@PathVariable Long id) {
        return new ResponseEntity<>(compliantService.getAllCompliantsByHandler(id), HttpStatus.OK);
    }
    
    @GetMapping("/compliants/handler/count/{id}")
    @PreAuthorize("hasAuthority('SCOPE_HANDLER') and (#id == T(com.bitsathy.quarters.security.JwtUtils).getUserIdFromToken(authentication))")
    public ResponseEntity<Map<String, Long>> getHandlerCompliantsCount(@PathVariable Long id) {
        return new ResponseEntity<>(compliantService.getHandlerCompliantCount(id), HttpStatus.OK);
    }
    
    @GetMapping("/compliants/search")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public List<Compliant> searchCompliant(@RequestParam String keyword) {
        return compliantService.searchCompliant(keyword);
    }
    
    @GetMapping("/compliants/handler/{id}/search")
    @PreAuthorize("hasAuthority('SCOPE_HANDLER')")
    public List<Compliant> searchHandlerCompliant(@RequestParam String keyword, @PathVariable Long id) {
        return compliantService.searchHandlerCompliant(keyword, id);
    }
    
    @PostMapping("/compliants/{id}/updateStatus") // TODO
    @PreAuthorize("hasAuthority('SCOPE_ADMIN') or hasAuthority('SCOPE_HANDLER')") //TODO
    public ResponseEntity<?> updateCompliantStatus(@PathVariable Long id, @RequestBody ObjectNode json) {
        try {
            compliantService.updateService(id, json.get("status").asText());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }
}