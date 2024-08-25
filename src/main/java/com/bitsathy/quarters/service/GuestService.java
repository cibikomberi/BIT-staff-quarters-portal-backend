package com.bitsathy.quarters.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bitsathy.quarters.model.Guest;
import com.bitsathy.quarters.repo.GuestRepo;


@Service
public class GuestService {
    @Autowired
    private GuestRepo guestRepo;

    public List<Guest> getGuests(){
        return guestRepo.findAll();
    }

    public List<Guest> addGuests(List<Guest> guests){
        return guestRepo.saveAll(guests);
    }
}
