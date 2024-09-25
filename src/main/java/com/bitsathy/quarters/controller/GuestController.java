package com.bitsathy.quarters.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.bitsathy.quarters.model.Guest;
import com.bitsathy.quarters.service.GuestService;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
@CrossOrigin
public class GuestController {

    @Autowired
    private GuestService guestService;

    @GetMapping("/guests")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public List<Guest> getAllGuests(){
        return guestService.getAllGuests();
    }
    
    @GetMapping("/guests/user/{id}")
    @PreAuthorize("hasAuthority('SCOPE_USER') and (#id == T(com.bitsathy.quarters.security.JwtUtils).getUserIdFromToken(authentication))")
    public List<Guest> getGuests(@PathVariable Long id) {
        return guestService.getGuests(id);
    }

    @PostMapping("/guests/user/{id}")
    @PreAuthorize("hasAuthority('SCOPE_USER') and (#id == T(com.bitsathy.quarters.security.JwtUtils).getUserIdFromToken(authentication))")
    public ResponseEntity<?> postMethodName(@RequestBody List<Guest> guests,@PathVariable Long id) {
        try {
            return new ResponseEntity<>(guestService.addGuests(guests, id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/guests/checkout/{id}")
    @PreAuthorize("hasAuthority('SCOPE_USER') and (#id == T(com.bitsathy.quarters.security.JwtUtils).getUserIdFromToken(authentication))")
    public ResponseEntity<?> guestCheckout(@PathVariable Long id, @RequestBody ObjectNode json) {
        try {
            return new ResponseEntity<>(guestService.guestCheckout(json.get("guestId").asLong(), id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
