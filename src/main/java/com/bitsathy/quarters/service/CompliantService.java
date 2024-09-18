package com.bitsathy.quarters.service;

import java.time.LocalDateTime;
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

    @Autowired
    private CompliantRepo compliantRepo;

    @Autowired
    private HandlerRepo handlerRepo;

    public List<Compliant> getAllCompliants() {
        return compliantRepo.findAll();
    }

    public Map<String, Object> getCompliantCount() {
        Map<String, Object> response = new HashMap<>();
        response.put("issued", compliantRepo.countIssuedComplaintsToday());
        response.put("pending", compliantRepo.countPendingComplaints());
        response.put("resolved", compliantRepo.countResolvedComplaintsToday());
        return response;
    }

    public Map<String, Object> getHandlerCompliantCount(Long id) {
        Map<String, Object> response = new HashMap<>();
        response.put("issued", compliantRepo.countIssuedComplaintsHandler(id));
        response.put("pending", compliantRepo.countPendingComplaintsHandler(id));
        response.put("resolved", compliantRepo.countResolvedComplaintsHandler(id));
        return response;
    }

    public Compliant getCompliantById(Long id) {
        return compliantRepo.findById(id).orElse(null);
    }

    public Compliant newCompliant(Compliant compliant, Long id) throws Exception {

        if (compliant.getStatus() == null) {
            compliant.setStatus("Initiated");
        }
        Faculty issuedBy = Faculty.builder().id(id).build();
        compliant.setIssuedBy(issuedBy);

        Handler handlerWithMinCount = handlerRepo.findByCategory(compliant.getCategory())
                .stream()
                .min(Comparator.comparing(Handler::getActiveCount))
                .orElseThrow(() -> new RuntimeException("No Handlers Available"));
                
        handlerWithMinCount.setActiveCount(handlerWithMinCount.getActiveCount() + 1);
        handlerRepo.save(handlerWithMinCount);

        compliant.setAssignedTo(handlerWithMinCount);

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

    public void updateService(Long id, String status) {
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
        throw new RuntimeException("Invalid status");
    }
}