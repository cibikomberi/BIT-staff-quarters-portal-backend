package com.bitsathy.quarters.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bitsathy.quarters.model.Checkouts;
import com.bitsathy.quarters.repo.CheckoutRepo;

@Service
public class CheckoutService {

    @Autowired
    private CheckoutRepo checkoutRepo;

    public List<Checkouts> getAllCheckouts() {
        List<Checkouts> checkouts = checkoutRepo.findAll();
        for (Checkouts checkout : checkouts) {
            checkout.setPin(null);
        }
        return checkouts;
    }

    public List<Checkouts> searchCheckouts(String keyword) {
        List<Checkouts> checkouts = checkoutRepo.searchCheckouts(keyword);
        for (Checkouts checkout : checkouts) {
            checkout.setPin(null);
        }
        return checkouts;
    }

    public List<Checkouts> getAllCheckoutsByUser(Long id) {
        return checkoutRepo.findByFaculty_Id(id);
    }

    public List<Checkouts> getCheckoutsByPin(String pin) {
        return checkoutRepo.findByPin(pin);
    }

    public boolean checkout(Long id, String pin) throws Exception {
        Checkouts checkout = checkoutRepo.findById(id).get();
        if (checkout.getPin().equals(pin)) {
            checkoutRepo.delete(checkout);
            return true;
        }
        throw new Exception("Pin not verified");
    }


}
