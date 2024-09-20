package com.bitsathy.quarters.service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bitsathy.quarters.model.Faculty;
import com.bitsathy.quarters.model.Handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bitsathy.quarters.model.Compliant;
import com.bitsathy.quarters.repo.CompliantRepo;
import com.bitsathy.quarters.repo.HandlerRepo;

@Service
public class CompliantService {

    public static List<String> categories = Arrays.asList("Plumbing", "Electrical", "Carpentering", "Gardening", "Others");

    @Autowired
    private CompliantRepo compliantRepo;
    @Autowired
    private HandlerRepo handlerRepo;

    //Return all compliants
    public List<Compliant> getAllCompliants() {
        return compliantRepo.findAll();
    }

    // Returns compliant counts for admin
    public Map<String, Long> getCompliantCount() {
        Map<String, Long> response = new HashMap<>();
        response.put("issued", compliantRepo.countIssuedComplaintsToday());
        response.put("pending", compliantRepo.countPendingComplaints());
        response.put("resolved", compliantRepo.countResolvedComplaintsToday());
        return response;
    }

    public Map<String, Long> getHandlerCompliantCount(Long id) {
        Map<String, Long> response = new HashMap<>();
        response.put("issued", compliantRepo.countIssuedComplaintsHandler(id));
        response.put("pending", compliantRepo.countPendingComplaintsHandler(id));
        response.put("resolved", compliantRepo.countResolvedComplaintsHandler(id));
        return response;
    }

    public Compliant getCompliantById(Long id) {
        return compliantRepo.findById(id).orElse(null);
    }

    public Compliant newCompliant(Compliant compliant, Long id) throws Exception {

        compliant.setStatus("Initiated");

        // Verify all fields
        if (!categories.contains(compliant.getCategory())) {
            throw new Exception("Category is invalid");
        }
        if (compliant.getTitle() == null || compliant.getTitle().trim().equals("")) {
            throw new Exception("Invalid title");
        }
        if (compliant.getDescription() == null || compliant.getDescription().trim().equals("")) {
            throw new Exception("Please provide description");
        }
        if (compliant.getAvailableTime() == null || compliant.getAvailableTime().trim().equals("")) {
            compliant.setAvailableTime("Anytime");
        }

        // Create a dummy faculty with same id 
        // instead of hitting db for fetching faculty
        Faculty issuedBy = Faculty.builder().id(id).build();
        compliant.setIssuedBy(issuedBy);

        // Assign compliant to handler with minimum active count
        Handler handlerWithMinCount = handlerRepo.findByCategory(compliant.getCategory())
                .stream()
                .min(Comparator.comparing(Handler::getActiveCount))
                .orElseThrow(() -> new RuntimeException("No Handlers Available"));
                
        handlerWithMinCount.setActiveCount(handlerWithMinCount.getActiveCount() + 1);
        handlerRepo.save(handlerWithMinCount);

        compliant.setAssignedTo(handlerWithMinCount);

        // Set current time
        compliant.setIssuedOn(LocalDateTime.now());
        return compliantRepo.save(compliant);
    }

    public List<Compliant> getAllCompliantsByUser(Long id) {
        return compliantRepo.findByIssuedBy_Id(id);

    }

    public List<Compliant> searchCompliant(String keyword) {
        return compliantRepo.searchCompliants(keyword);
    }

    public List<Compliant> getAllCompliantsByHandler(Long id) {
        return compliantRepo.findByAssignedTo_Id(id);
    }

    public List<Compliant> searchHandlerCompliant(String keyword, Long username) {
        return compliantRepo.searchHandlerCompliants(keyword, username);
    }

    public void updateService(Long id, String status) throws Exception {
        Compliant compliant = compliantRepo.findById(id).get();

        if (status.equals("Accept")) {
            if (compliant.getStatus().equals("Initiated")) {
                compliant.setStatus("Ongoing");
                compliantRepo.save(compliant);
                return;
            }
        }
        if (status.equals("Reject")) {
            if (compliant.getStatus().equals("Initiated")) {
                compliant.setStatus("Rejected");
                compliantRepo.save(compliant);
                return;
            }
        }
        if (status.equals("Completed")) {
            if (compliant.getStatus().equals("Ongoing")) {
                compliant.setStatus("Completed");
                compliant.setResolvedOn(LocalDateTime.now());
                compliantRepo.save(compliant);
                return;
            }
        }
        throw new Exception("Invalid status");
    }
}