package com.bitsathy.quarters.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import com.bitsathy.quarters.model.Checkouts;
import com.bitsathy.quarters.service.CheckoutService;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@CrossOrigin
public class CheckoutController {

    @Autowired
    private CheckoutService checkoutService;

    @GetMapping("/checkouts")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public List<Checkouts> getAllCheckouts() {
        return checkoutService.getAllCheckouts();
    }

    @GetMapping("/checkouts/search")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public List<Checkouts> searchCheckouts(@RequestParam String keyword) {
        return checkoutService.searchCheckouts(keyword);
    }

    @GetMapping("/checkouts/{id}")
    @PreAuthorize("hasAuthority('SCOPE_USER') and #id == T(com.bitsathy.quarters.security.JwtUtils).getUserIdFromToken(authentication)")
    public List<Checkouts> getAllCheckoutsByUser(@PathVariable Long id) {
        return checkoutService.getAllCheckoutsByUser(id);
    }

    @GetMapping("/checkouts/pin/{pin}")
    @PreAuthorize("hasAuthority('SCOPE_SECURITY')")
    public List<Checkouts> getCheckoutsByPin(@PathVariable String pin) {
        return checkoutService.getCheckoutsByPin(pin);
    }

    @PostMapping("/checkouts/checkout/{id}")
    @PreAuthorize("hasAuthority('SCOPE_SECURITY')")
    public ResponseEntity<?> checkout(@PathVariable Long id, @RequestBody ObjectNode json) {
        try {
            checkoutService.checkout(id, json.get("PIN").asText());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
