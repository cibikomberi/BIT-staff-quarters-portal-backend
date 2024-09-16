package com.bitsathy.quarters.service;

import java.util.List;

import com.bitsathy.quarters.model.Faculty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bitsathy.quarters.model.Guest;
import com.bitsathy.quarters.repo.GuestRepo;


@Service
public class GuestService {
    @Autowired
    private GuestRepo guestRepo;

    public List<Guest> getAllGuests(){
        return guestRepo.findAll();
    }

    public List<Guest> getGuests(Long id){
        return guestRepo.findByFaculty_Id(id);
    }

    public List<Guest> addGuests(List<Guest> guests,Long id){
        Faculty faculty = Faculty.builder().id(id).build();
        guests.forEach(guest -> guest.setFaculty(faculty));
        return guestRepo.saveAll(guests);
    }
}
