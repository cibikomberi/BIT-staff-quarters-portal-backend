package com.bitsathy.quarters.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.bitsathy.quarters.model.Guest;
import com.bitsathy.quarters.service.GuestService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

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
    
    @GetMapping("/guests/{id}")
    @PreAuthorize("hasAuthority('SCOPE_USER') and (#id == T(com.bitsathy.quarters.security.JwtUtils).getUserIdFromToken(authentication))")
    public List<Guest> getGuests(@PathVariable Long id) {
        return guestService.getGuests(id);
    }

    @PostMapping("/guests/{id}")
    @PreAuthorize("hasAuthority('SCOPE_USER') and (#id == T(com.bitsathy.quarters.security.JwtUtils).getUserIdFromToken(authentication))")
    public List<Guest> postMethodName(@RequestBody List<Guest> guests,@PathVariable Long id) {
        return guestService.addGuests(guests,id);
    }
}
