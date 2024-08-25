package com.bitsathy.quarters.controller;

import org.springframework.web.bind.annotation.RestController;

import com.bitsathy.quarters.model.Guest;
import com.bitsathy.quarters.service.GuestService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@CrossOrigin
public class GuestController {

    @Autowired
    private GuestService guestService;

    @GetMapping("/guests")
    public List<Guest> getGuests() {
        return guestService.getGuests();
    }

    @PostMapping("/guests")
    public List<Guest> postMethodName(@RequestBody List<Guest> guests) {
        return guestService.addGuests(guests);
    }
    
    
}
