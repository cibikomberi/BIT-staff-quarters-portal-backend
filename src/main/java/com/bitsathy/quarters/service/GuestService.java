package com.bitsathy.quarters.service;

import java.security.SecureRandom;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import com.bitsathy.quarters.model.Checkouts;
import com.bitsathy.quarters.model.Faculty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bitsathy.quarters.model.Guest;
import com.bitsathy.quarters.repo.CheckoutRepo;
import com.bitsathy.quarters.repo.GuestRepo;

@Service
public class GuestService {
    @Autowired
    private GuestRepo guestRepo;
    @Autowired
    private CheckoutRepo checkoutRepo;

    public List<Guest> getAllGuests() {
        return guestRepo.findAll();
    }

    public List<Guest> getGuests(Long id) {
        return guestRepo.findByFaculty_Id(id);
    }

    public List<Guest> addGuests(List<Guest> guests, Long id) throws Exception {

        // Create a dummy faculty with same id
        // instead of hitting db for fetching faculty
        Faculty faculty = Faculty.builder().id(id).build();

        Date lowerBound = new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime();
        Date upperBound = new GregorianCalendar(2050, Calendar.DECEMBER, 31).getTime();

        for (Guest guest : guests) {

            // Verify fields
            if (guest.getName() == null || guest.getName().trim().equals("")) {
                throw new Exception("Invalid guest name");
            }
            if (guest.getPlace() == null || guest.getPlace().trim().equals("")) {
                throw new Exception("Invalid place");
            }
            if (guest.getFromDate() == null
                    || !(guest.getFromDate().after(lowerBound) && guest.getFromDate().before(upperBound))) {
                throw new Exception("From date is out of range");
            }
            if (guest.getToDate() == null
                    || !(guest.getToDate().after(lowerBound) && guest.getToDate().before(upperBound))) {
                throw new Exception("To date is out of range");
            }
            if (guest.getToDate().before(guest.getFromDate())) {
                throw new Exception("To date is before from date");
            }

            // Set dummy faculty to each guest
            guest.setFaculty(faculty);
        }

        return guestRepo.saveAll(guests);
    }

    public String guestCheckout(Long guestId, Long id) throws Exception {
        Guest guest = guestRepo.findById(guestId).get();
        if (guest.getFaculty().getId() == id) {
            SecureRandom random = new SecureRandom();
            int num = random.nextInt(1000000);
            String pin = String.format("%06d", num);

            checkoutRepo.save(Checkouts.builder()
                    .name(guest.getName())
                    .type("Guest")
                    .pin(pin)
                    .faculty(guest.getFaculty())
                    .build());
                    
            guestRepo.delete(guest);
            return "Ok";
        }
        throw new Exception("Invalid arguments");
    }
}
